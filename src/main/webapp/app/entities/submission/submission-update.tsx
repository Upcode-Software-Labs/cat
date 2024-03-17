import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm, ValidatedBlobField } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IUserAssessment } from 'app/shared/model/user-assessment.model';
import { getEntities as getUserAssessments } from 'app/entities/user-assessment/user-assessment.reducer';
import { IUser } from 'app/shared/model/user.model';
import { getUsers } from 'app/modules/administration/user-management/user-management.reducer';
import { IAssessment } from 'app/shared/model/assessment.model';
import { getEntities as getAssessments } from 'app/entities/assessment/assessment.reducer';
import { ISubmission } from 'app/shared/model/submission.model';
import { getEntity, updateEntity, createEntity, reset } from './submission.reducer';

export const SubmissionUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const userAssessments = useAppSelector(state => state.userAssessment.entities);
  const users = useAppSelector(state => state.userManagement.users);
  const assessments = useAppSelector(state => state.assessment.entities);
  const submissionEntity = useAppSelector(state => state.submission.entity);
  const loading = useAppSelector(state => state.submission.loading);
  const updating = useAppSelector(state => state.submission.updating);
  const updateSuccess = useAppSelector(state => state.submission.updateSuccess);

  const handleClose = () => {
    navigate('/submission' + location.search);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getUserAssessments({}));
    dispatch(getUsers({}));
    dispatch(getAssessments({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  // eslint-disable-next-line complexity
  const saveEntity = values => {
    if (values.pointsScored !== undefined && typeof values.pointsScored !== 'number') {
      values.pointsScored = Number(values.pointsScored);
    }

    const entity = {
      ...submissionEntity,
      ...values,
      forAssignment: userAssessments.find(it => it.id.toString() === values.forAssignment.toString()),
      user: users.find(it => it.id.toString() === values.user.toString()),
      assessment: assessments.find(it => it.id.toString() === values.assessment.toString()),
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
          ...submissionEntity,
          forAssignment: submissionEntity?.forAssignment?.id,
          user: submissionEntity?.user?.id,
          assessment: submissionEntity?.assessment?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="catApp.submission.home.createOrEditLabel" data-cy="SubmissionCreateUpdateHeading">
            <Translate contentKey="catApp.submission.home.createOrEditLabel">Create or edit a Submission</Translate>
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
                  id="submission-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('catApp.submission.githubUrl')}
                id="submission-githubUrl"
                name="githubUrl"
                data-cy="githubUrl"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedBlobField
                label={translate('catApp.submission.screenshots')}
                id="submission-screenshots"
                name="screenshots"
                data-cy="screenshots"
                isImage
                accept="image/*"
              />
              <ValidatedField
                label={translate('catApp.submission.videoExplanation')}
                id="submission-videoExplanation"
                name="videoExplanation"
                data-cy="videoExplanation"
                type="text"
              />
              <ValidatedField
                label={translate('catApp.submission.textDescription')}
                id="submission-textDescription"
                name="textDescription"
                data-cy="textDescription"
                type="text"
              />
              <ValidatedField
                label={translate('catApp.submission.feedback')}
                id="submission-feedback"
                name="feedback"
                data-cy="feedback"
                type="textarea"
              />
              <ValidatedField
                label={translate('catApp.submission.pointsScored')}
                id="submission-pointsScored"
                name="pointsScored"
                data-cy="pointsScored"
                type="text"
              />
              <ValidatedField
                id="submission-forAssignment"
                name="forAssignment"
                data-cy="forAssignment"
                label={translate('catApp.submission.forAssignment')}
                type="select"
              >
                <option value="" key="0" />
                {userAssessments
                  ? userAssessments.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField id="submission-user" name="user" data-cy="user" label={translate('catApp.submission.user')} type="select">
                <option value="" key="0" />
                {users
                  ? users.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="submission-assessment"
                name="assessment"
                data-cy="assessment"
                label={translate('catApp.submission.assessment')}
                type="select"
              >
                <option value="" key="0" />
                {assessments
                  ? assessments.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/submission" replace color="info">
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

export default SubmissionUpdate;
