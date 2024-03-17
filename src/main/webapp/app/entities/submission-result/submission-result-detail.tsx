import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, byteSize } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './submission-result.reducer';

export const SubmissionResultDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const submissionResultEntity = useAppSelector(state => state.submissionResult.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="submissionResultDetailsHeading">
          <Translate contentKey="catApp.submissionResult.detail.title">SubmissionResult</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{submissionResultEntity.id}</dd>
          <dt>
            <span id="totalPoints">
              <Translate contentKey="catApp.submissionResult.totalPoints">Total Points</Translate>
            </span>
          </dt>
          <dd>{submissionResultEntity.totalPoints}</dd>
          <dt>
            <span id="detailedResults">
              <Translate contentKey="catApp.submissionResult.detailedResults">Detailed Results</Translate>
            </span>
          </dt>
          <dd>{submissionResultEntity.detailedResults}</dd>
          <dt>
            <span id="feedback">
              <Translate contentKey="catApp.submissionResult.feedback">Feedback</Translate>
            </span>
          </dt>
          <dd>{submissionResultEntity.feedback}</dd>
          <dt>
            <Translate contentKey="catApp.submissionResult.submission">Submission</Translate>
          </dt>
          <dd>{submissionResultEntity.submission ? submissionResultEntity.submission.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/submission-result" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/submission-result/${submissionResultEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default SubmissionResultDetail;
