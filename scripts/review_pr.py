import os
import requests
from urllib.parse import urljoin

OPENAI_API_KEY = os.getenv("OPENAI_API_KEY")
GITHUB_TOKEN = os.getenv("GEESON_REPO_TOKEN")
REPO = os.getenv("GITHUB_REPOSITORY")
PR_NUMBER = os.getenv("GITHUB_REF").split("/")[-1]
API_BASE = f"https://api.github.com/repos/{REPO}"

HEADERS = {
    "Authorization": f"token {GITHUB_TOKEN}",
    "Accept": "application/vnd.github.v3+json"
}

def get_diff():
    url = f"{API_BASE}/pulls/{PR_NUMBER}"
    resp = requests.get(url, headers=HEADERS)

    if resp.status_code != 200:
        raise Exception(f"Failed to get PR info: {resp.status_code} {resp.text}")

    pr = resp.json()
    if 'diff_url' not in pr:
        raise Exception(f"'diff_url' not found in PR data: {pr}")

    diff_url = pr['diff_url']
    diff = requests.get(diff_url, headers=HEADERS).text
    return diff

def review_with_chatgpt(diff):
    url = "https://api.openai.com/v1/chat/completions"
    headers = {
        "Authorization": f"Bearer {OPENAI_API_KEY}",
        "Content-Type": "application/json"
    }
    body = {
        "model": "gpt-4",  # 또는 gpt-4o
        "messages": [
            {"role": "system", "content": "You are a senior developer doing a code review."},
            {"role": "user", "content": f"Please review the following code diff:\n\n{diff}"}
        ],
        "temperature": 0.3
    }
    response = requests.post(url, headers=headers, json=body)
    return response.json()["choices"][0]["message"]["content"]

def post_comment(review_text):
    url = f"{API_BASE}/issues/{PR_NUMBER}/comments"
    data = {"body": review_text}
    requests.post(url, headers=HEADERS, json=data)

if __name__ == "__main__":
    diff = get_diff()
    review = review_with_chatgpt(diff)
    post_comment(review)