import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './user-assessment.reducer';

export const UserAssessmentDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const userAssessmentEntity = useAppSelector(state => state.userAssessment.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="userAssessmentDetailsHeading">
          <Translate contentKey="catApp.userAssessment.detail.title">UserAssessment</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{userAssessmentEntity.id}</dd>
          <dt>
            <span id="status">
              <Translate contentKey="catApp.userAssessment.status">Status</Translate>
            </span>
          </dt>
          <dd>{userAssessmentEntity.status}</dd>
          <dt>
            <span id="assignedAt">
              <Translate contentKey="catApp.userAssessment.assignedAt">Assigned At</Translate>
            </span>
          </dt>
          <dd>
            {userAssessmentEntity.assignedAt ? (
              <TextFormat value={userAssessmentEntity.assignedAt} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="deadline">
              <Translate contentKey="catApp.userAssessment.deadline">Deadline</Translate>
            </span>
          </dt>
          <dd>
            {userAssessmentEntity.deadline ? (
              <TextFormat value={userAssessmentEntity.deadline} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <Translate contentKey="catApp.userAssessment.submittedByUser">Submitted By User</Translate>
          </dt>
          <dd>{userAssessmentEntity.submittedByUser ? userAssessmentEntity.submittedByUser.id : ''}</dd>
          <dt>
            <Translate contentKey="catApp.userAssessment.user">User</Translate>
          </dt>
          <dd>{userAssessmentEntity.user ? userAssessmentEntity.user.id : ''}</dd>
          <dt>
            <Translate contentKey="catApp.userAssessment.assessment">Assessment</Translate>
          </dt>
          <dd>{userAssessmentEntity.assessment ? userAssessmentEntity.assessment.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/user-assessment" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/user-assessment/${userAssessmentEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default UserAssessmentDetail;
