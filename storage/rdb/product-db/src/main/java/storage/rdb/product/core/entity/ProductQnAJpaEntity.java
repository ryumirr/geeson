package storage.rdb.product.core.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "product_qna")
public class ProductQnAJpaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long qnaId;

    private Long productId;
    private Long customerId;
    private String question;
    private String answer;
    private String status;
    private LocalDateTime createdAt;
}
