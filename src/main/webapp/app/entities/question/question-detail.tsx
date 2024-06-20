import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, byteSize } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './question.reducer';

export const QuestionDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const questionEntity = useAppSelector(state => state.question.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="questionDetailsHeading">
          <Translate contentKey="catApp.question.detail.title">Question</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{questionEntity.id}</dd>
          <dt>
            <span id="questionText">
              <Translate contentKey="catApp.question.questionText">Question Text</Translate>
            </span>
          </dt>
          <dd>{questionEntity.questionText}</dd>
          <dt>
            <span id="codeSnippet">
              <Translate contentKey="catApp.question.codeSnippet">Code Snippet</Translate>
            </span>
          </dt>
          <dd>{questionEntity.codeSnippet}</dd>
          <dt>
            <span id="resources">
              <Translate contentKey="catApp.question.resources">Resources</Translate>
            </span>
          </dt>
          <dd>{questionEntity.resources}</dd>
          <dt>
            <span id="points">
              <Translate contentKey="catApp.question.points">Points</Translate>
            </span>
          </dt>
          <dd>{questionEntity.points}</dd>
          <dt>
            <Translate contentKey="catApp.question.assignment">Assessment</Translate>
          </dt>
          <dd>{questionEntity.assignment ? questionEntity.assignment.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/question" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/question/${questionEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default QuestionDetail;
