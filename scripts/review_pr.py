import os
import requests
import json

OPENAI_API_KEY = os.getenv("OPENAI_API_KEY")
GITHUB_TOKEN = os.getenv("GITHUB_TOKEN")
REPO = os.getenv("GITHUB_REPOSITORY")  # ex: jeongjin984/geeson
API_BASE = f"https://api.github.com/repos/{REPO}"

def get_pr_number():
    with open(os.getenv("GITHUB_EVENT_PATH")) as f:
        event = json.load(f)
    return event["pull_request"]["number"]

def get_diff():
    pr_number = get_pr_number()
    url = f"{API_BASE}/pulls/{pr_number}"
    resp = requests.get(url, headers={
        "Authorization": f"token {GITHUB_TOKEN}",
        "Accept": "application/vnd.github.v3+json"
    })

    if resp.status_code != 200:
        raise Exception(f"Failed to get PR info: {resp.status_code} {resp.text}")

    pr = resp.json()
    if 'diff_url' not in pr:
        raise Exception(f"'diff_url' not found in PR data: {pr}")

    diff_resp = requests.get(pr['diff_url'], headers={
        "Authorization": f"token {GITHUB_TOKEN}"
    })

    if diff_resp.status_code != 200:
        raise Exception(f"Failed to get diff: {diff_resp.status_code} {diff_resp.text}")

    return diff_resp.text