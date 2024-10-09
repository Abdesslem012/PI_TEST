/*package com.example.demo.Repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.repository.query.FluentQuery;
import org.springframework.stereotype.Repository;
import org.springframework.data.mongodb.core.query.Query;
import java.util.List;
import java.util.Optional;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.ArrayOperators;
import java.util.function.Function;

@Repository
public class CustomClassesRepositoryImpl implements CustomClassesRepository {

    private final MongoTemplate mongoTemplate;

    @Autowired
    public CustomClassesRepositoryImpl(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public List<Classes> findClassesByCriteria(String name, String description) {
        Query query = new Query();

        if (name != null) {
            query.addCriteria(Criteria.where("name").is(name));
        }

        if (description != null) {
            query.addCriteria(Criteria.where("description").regex(description, "i"));
        }

        return mongoTemplate.find(query, Classes.class);
    }


    @Override
    public List<ClassesStats> getClassesStats() {
        Aggregation aggregation = Aggregation.newAggregation(
                Aggregation.lookup("students", "studentId", "_id", "students"),
                Aggregation.project("name", "description")
                        .and(ArrayOperators.Size.lengthOfArray("students")).as("numStudents")
        );

        AggregationResults<ClassesStats> results = mongoTemplate.aggregate(aggregation, "classes", ClassesStats.class);
        return results.getMappedResults();
    }

    @Override
    public int countStudentsByClassId(Long classId) {
        return 0;
    }

    @Override
    public <S extends Classes> S insert(S entity) {
        return null;
    }

    @Override
    public <S extends Classes> List<S> insert(Iterable<S> entities) {
        return null;
    }

    @Override
    public <S extends Classes> Optional<S> findOne(Example<S> example) {
        return Optional.empty();
    }

    @Override
    public <S extends Classes> List<S> findAll(Example<S> example) {
        return null;
    }

    @Override
    public <S extends Classes> List<S> findAll(Example<S> example, Sort sort) {
        return null;
    }

    @Override
    public <S extends Classes> Page<S> findAll(Example<S> example, Pageable pageable) {
        return null;
    }

    @Override
    public <S extends Classes> long count(Example<S> example) {
        return 0;
    }

    @Override
    public <S extends Classes> boolean exists(Example<S> example) {
        return false;
    }


    @Override
    public <S extends Classes, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
        return null;
    }

    @Override
    public <S extends Classes> S save(S entity) {
        return null;
    }

    @Override
    public <S extends Classes> List<S> saveAll(Iterable<S> entities) {
        return null;
    }

    @Override
    public Optional<Classes> findById(Long aLong) {
        return Optional.empty();
    }

    @Override
    public boolean existsById(Long aLong) {
        return false;
    }

    @Override
    public List<Classes> findAll() {
        return null;
    }

    @Override
    public List<Classes> findAllById(Iterable<Long> longs) {
        return null;
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public void deleteById(Long aLong) {

    }

    @Override
    public void delete(Classes entity) {

    }

    @Override
    public void deleteAllById(Iterable<? extends Long> longs) {

    }

    @Override
    public void deleteAll(Iterable<? extends Classes> entities) {

    }

    @Override
    public void deleteAll() {

    }

    @Override
    public List<Classes> findAll(Sort sort) {
        return null;
    }

    @Override
    public Page<Classes> findAll(Pageable pageable) {
        return null;
    }

    // Autres méthodes personnalisées si nécessaire
}

*/