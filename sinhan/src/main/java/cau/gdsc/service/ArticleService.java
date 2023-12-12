package cau.gdsc.service;

import cau.gdsc.config.api.ResponseCode;
import cau.gdsc.config.exception.BaseException;
import cau.gdsc.domain.Article;
import cau.gdsc.dto.ArticleAddReqDto;
import cau.gdsc.repository.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArticleService {
    private final ArticleRepository articleRepository;

    @Autowired
    public ArticleService(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    public List<Article> getArticles() {
        return articleRepository.findAll();
    }

    public Article getArticleById(Long id) {
        return articleRepository.findById(id).orElseThrow(() -> new BaseException(ResponseCode.NOT_FOUND));
    }

    public Article createArticle(ArticleAddReqDto articleAddReqDto) {
        return articleRepository.save(Article.createArticle(articleAddReqDto.getTitle(), articleAddReqDto.getContent()));
    }

    public Article updateArticle(Long id, ArticleAddReqDto articleAddReqDto) {
        Article article = getArticleById(id); // 존재 확인
        Article updatedArticle = Article.builder().id(id).title(articleAddReqDto.getTitle()).content(articleAddReqDto.getContent()).build();
        return articleRepository.save(updatedArticle);
    }

    public void deleteArticleById(Long id) {
        if (articleRepository.existsById(id)) articleRepository.deleteById(id);
        else throw new IllegalArgumentException("Invalid article Id:" + id);
    }
}
