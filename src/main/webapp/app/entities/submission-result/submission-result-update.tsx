import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { ISubmission } from 'app/shared/model/submission.model';
import { getEntities as getSubmissions } from 'app/entities/submission/submission.reducer';
import { ISubmissionResult } from 'app/shared/model/submission-result.model';
import { getEntity, updateEntity, createEntity, reset } from './submission-result.reducer';

export const SubmissionResultUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const submissions = useAppSelector(state => state.submission.entities);
  const submissionResultEntity = useAppSelector(state => state.submissionResult.entity);
  const loading = useAppSelector(state => state.submissionResult.loading);
  const updating = useAppSelector(state => state.submissionResult.updating);
  const updateSuccess = useAppSelector(state => state.submissionResult.updateSuccess);

  const handleClose = () => {
    navigate('/submission-result');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getSubmissions({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  // eslint-disable-next-line complexity
  const saveEntity = values => {
    if (values.totalPoints !== undefined && typeof values.totalPoints !== 'number') {
      values.totalPoints = Number(values.totalPoints);
    }

    const entity = {
      ...submissionResultEntity,
      ...values,
      submission: submissions.find(it => it.id.toString() === values.submission.toString()),
    };

    if (isNew) {
      dispatch(createEntity(entity));
    } else {
      dispatch(updateEntity(entity));
    }
  };

  const defaultValues = () =>
    isNew
      ? {}
      : {
          ...submissionResultEntity,
          submission: submissionResultEntity?.submission?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="catApp.submissionResult.home.createOrEditLabel" data-cy="SubmissionResultCreateUpdateHeading">
            <Translate contentKey="catApp.submissionResult.home.createOrEditLabel">Create or edit a SubmissionResult</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? (
                <ValidatedField
                  name="id"
                  required
                  readOnly
                  id="submission-result-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('catApp.submissionResult.totalPoints')}
                id="submission-result-totalPoints"
                name="totalPoints"
                data-cy="totalPoints"
                type="text"
              />
              <ValidatedField
                label={translate('catApp.submissionResult.detailedResults')}
                id="submission-result-detailedResults"
                name="detailedResults"
                data-cy="detailedResults"
                type="textarea"
              />
              <ValidatedField
                label={translate('catApp.submissionResult.feedback')}
                id="submission-result-feedback"
                name="feedback"
                data-cy="feedback"
                type="textarea"
              />
              <ValidatedField
                id="submission-result-submission"
                name="submission"
                data-cy="submission"
                label={translate('catApp.submissionResult.submission')}
                type="select"
              >
                <option value="" key="0" />
                {submissions
                  ? submissions.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/submission-result" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">
                  <Translate contentKey="entity.action.back">Back</Translate>
                </span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" data-cy="entityCreateSaveButton" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp;
                <Translate contentKey="entity.action.save">Save</Translate>
              </Button>
            </ValidatedForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

export default SubmissionResultUpdate;
