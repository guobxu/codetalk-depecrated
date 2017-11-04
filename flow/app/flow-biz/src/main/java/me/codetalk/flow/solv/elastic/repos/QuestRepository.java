package me.codetalk.flow.solv.elastic.repos;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import me.codetalk.flow.solv.elastic.DocQuest;

public interface QuestRepository extends ElasticsearchRepository<DocQuest, String> {

}
