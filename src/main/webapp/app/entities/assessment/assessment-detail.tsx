import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, byteSize, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './assessment.reducer';

export const AssessmentDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const assessmentEntity = useAppSelector(state => state.assessment.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="assessmentDetailsHeading">
          <Translate contentKey="catApp.assessment.detail.title">Assessment</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{assessmentEntity.id}</dd>
          <dt>
            <span id="title">
              <Translate contentKey="catApp.assessment.title">Title</Translate>
            </span>
          </dt>
          <dd>{assessmentEntity.title}</dd>
          <dt>
            <span id="description">
              <Translate contentKey="catApp.assessment.description">Description</Translate>
            </span>
          </dt>
          <dd>{assessmentEntity.description}</dd>
          <dt>
            <span id="languageFramework">
              <Translate contentKey="catApp.assessment.languageFramework">Language Framework</Translate>
            </span>
          </dt>
          <dd>{assessmentEntity.languageFramework}</dd>
          <dt>
            <span id="difficultyLevel">
              <Translate contentKey="catApp.assessment.difficultyLevel">Difficulty Level</Translate>
            </span>
          </dt>
          <dd>{assessmentEntity.difficultyLevel}</dd>
          <dt>
            <span id="timeLimit">
              <Translate contentKey="catApp.assessment.timeLimit">Time Limit</Translate>
            </span>
          </dt>
          <dd>{assessmentEntity.timeLimit}</dd>
          <dt>
            <span id="type">
              <Translate contentKey="catApp.assessment.type">Type</Translate>
            </span>
          </dt>
          <dd>{assessmentEntity.type}</dd>
          <dt>
            <span id="validationCriteria">
              <Translate contentKey="catApp.assessment.validationCriteria">Validation Criteria</Translate>
            </span>
          </dt>
          <dd>{assessmentEntity.validationCriteria}</dd>
          <dt>
            <span id="question">
              <Translate contentKey="catApp.assessment.question">Question</Translate>
            </span>
          </dt>
          <dd>{assessmentEntity.question}</dd>
          <dt>
            <span id="maxPoints">
              <Translate contentKey="catApp.assessment.maxPoints">Max Points</Translate>
            </span>
          </dt>
          <dd>{assessmentEntity.maxPoints}</dd>
          <dt>
            <span id="deadline">
              <Translate contentKey="catApp.assessment.deadline">Deadline</Translate>
            </span>
          </dt>
          <dd>
            {assessmentEntity.deadline ? <TextFormat value={assessmentEntity.deadline} type="date" format={APP_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <Translate contentKey="catApp.assessment.assignedToUser">Assigned To User</Translate>
          </dt>
          <dd>{assessmentEntity.assignedToUser ? assessmentEntity.assignedToUser.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/assessment" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/assessment/${assessmentEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default AssessmentDetail;
