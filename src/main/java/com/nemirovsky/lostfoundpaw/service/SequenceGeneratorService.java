package com.nemirovsky.lostfoundpaw.service;

import com.nemirovsky.lostfoundpaw.model.DatabaseSequence;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.Objects;

import static org.springframework.data.mongodb.core.FindAndModifyOptions.options;
import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

@Slf4j
@Service
@RequiredArgsConstructor
public class SequenceGeneratorService {

    private final ReactiveMongoTemplate reactiveMongoTemplate;

    // TODO: get rid of block()
    public String generateSequence(String seqName) {
        long current = 0;
        DatabaseSequence counter = reactiveMongoTemplate.findAndModify(query(where("_id").is(seqName)),
                new Update().inc("seq",1), options().returnNew(true).upsert(true),
                DatabaseSequence.class).block();
        if (!Objects.isNull(counter)) current = counter.getSeq();
        return 'P' + Long.toHexString(Long.parseLong("10001", 16) + current);
    }
}
