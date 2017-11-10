package me.codetalk.flow.press.elastic.repos;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import me.codetalk.flow.press.elastic.DocArticle;

public interface ArticleRepository extends ElasticsearchRepository<DocArticle, String> {

}
