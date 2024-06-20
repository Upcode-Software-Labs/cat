import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, openFile, byteSize } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './submission.reducer';

export const SubmissionDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const submissionEntity = useAppSelector(state => state.submission.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="submissionDetailsHeading">
          <Translate contentKey="catApp.submission.detail.title">Submission</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{submissionEntity.id}</dd>
          <dt>
            <span id="githubUrl">
              <Translate contentKey="catApp.submission.githubUrl">Github Url</Translate>
            </span>
          </dt>
          <dd>{submissionEntity.githubUrl}</dd>
          <dt>
            <span id="screenshots">
              <Translate contentKey="catApp.submission.screenshots">Screenshots</Translate>
            </span>
          </dt>
          <dd>
            {submissionEntity.screenshots ? (
              <div>
                {submissionEntity.screenshotsContentType ? (
                  <a onClick={openFile(submissionEntity.screenshotsContentType, submissionEntity.screenshots)}>
                    <img
                      src={`data:${submissionEntity.screenshotsContentType};base64,${submissionEntity.screenshots}`}
                      style={{ maxHeight: '30px' }}
                    />
                  </a>
                ) : null}
                <span>
                  {submissionEntity.screenshotsContentType}, {byteSize(submissionEntity.screenshots)}
                </span>
              </div>
            ) : null}
          </dd>
          <dt>
            <span id="videoExplanation">
              <Translate contentKey="catApp.submission.videoExplanation">Video Explanation</Translate>
            </span>
          </dt>
          <dd>{submissionEntity.videoExplanation}</dd>
          <dt>
            <span id="textDescription">
              <Translate contentKey="catApp.submission.textDescription">Text Description</Translate>
            </span>
          </dt>
          <dd>{submissionEntity.textDescription}</dd>
          <dt>
            <span id="feedback">
              <Translate contentKey="catApp.submission.feedback">Feedback</Translate>
            </span>
          </dt>
          <dd>{submissionEntity.feedback}</dd>
          <dt>
            <span id="pointsScored">
              <Translate contentKey="catApp.submission.pointsScored">Points Scored</Translate>
            </span>
          </dt>
          <dd>{submissionEntity.pointsScored}</dd>
          <dt>
            <Translate contentKey="catApp.submission.forAssignment">For Assignment</Translate>
          </dt>
          <dd>{submissionEntity.forAssignment ? submissionEntity.forAssignment.id : ''}</dd>
          <dt>
            <Translate contentKey="catApp.submission.user">User</Translate>
          </dt>
          <dd>{submissionEntity.user ? submissionEntity.user.id : ''}</dd>
          <dt>
            <Translate contentKey="catApp.submission.assignment">Assessment</Translate>
          </dt>
          <dd>{submissionEntity.assignment ? submissionEntity.assignment.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/submission" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/submission/${submissionEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default SubmissionDetail;
