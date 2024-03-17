import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IUser } from 'app/shared/model/user.model';
import { getUsers } from 'app/modules/administration/user-management/user-management.reducer';
import { IAssessment } from 'app/shared/model/assessment.model';
import { getEntities as getAssessments } from 'app/entities/assessment/assessment.reducer';
import { IUserAssessment } from 'app/shared/model/user-assessment.model';
import { AssessmentStatus } from 'app/shared/model/enumerations/assessment-status.model';
import { getEntity, updateEntity, createEntity, reset } from './user-assessment.reducer';

export const UserAssessmentUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const users = useAppSelector(state => state.userManagement.users);
  const assessments = useAppSelector(state => state.assessment.entities);
  const userAssessmentEntity = useAppSelector(state => state.userAssessment.entity);
  const loading = useAppSelector(state => state.userAssessment.loading);
  const updating = useAppSelector(state => state.userAssessment.updating);
  const updateSuccess = useAppSelector(state => state.userAssessment.updateSuccess);
  const assessmentStatusValues = Object.keys(AssessmentStatus);

  const handleClose = () => {
    navigate('/user-assessment' + location.search);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

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
    values.assignedAt = convertDateTimeToServer(values.assignedAt);
    values.deadline = convertDateTimeToServer(values.deadline);

    const entity = {
      ...userAssessmentEntity,
      ...values,
      submittedByUser: users.find(it => it.id.toString() === values.submittedByUser.toString()),
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
      ? {
          assignedAt: displayDefaultDateTime(),
          deadline: displayDefaultDateTime(),
        }
      : {
          status: 'ASSIGNED',
          ...userAssessmentEntity,
          assignedAt: convertDateTimeFromServer(userAssessmentEntity.assignedAt),
          deadline: convertDateTimeFromServer(userAssessmentEntity.deadline),
          submittedByUser: userAssessmentEntity?.submittedByUser?.id,
          user: userAssessmentEntity?.user?.id,
          assessment: userAssessmentEntity?.assessment?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="catApp.userAssessment.home.createOrEditLabel" data-cy="UserAssessmentCreateUpdateHeading">
            <Translate contentKey="catApp.userAssessment.home.createOrEditLabel">Create or edit a UserAssessment</Translate>
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
                  id="user-assessment-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('catApp.userAssessment.status')}
                id="user-assessment-status"
                name="status"
                data-cy="status"
                type="select"
              >
                {assessmentStatusValues.map(assessmentStatus => (
                  <option value={assessmentStatus} key={assessmentStatus}>
                    {translate('catApp.AssessmentStatus.' + assessmentStatus)}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField
                label={translate('catApp.userAssessment.assignedAt')}
                id="user-assessment-assignedAt"
                name="assignedAt"
                data-cy="assignedAt"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('catApp.userAssessment.deadline')}
                id="user-assessment-deadline"
                name="deadline"
                data-cy="deadline"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                id="user-assessment-submittedByUser"
                name="submittedByUser"
                data-cy="submittedByUser"
                label={translate('catApp.userAssessment.submittedByUser')}
                type="select"
              >
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
                id="user-assessment-user"
                name="user"
                data-cy="user"
                label={translate('catApp.userAssessment.user')}
                type="select"
              >
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
                id="user-assessment-assessment"
                name="assessment"
                data-cy="assessment"
                label={translate('catApp.userAssessment.assessment')}
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
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/user-assessment" replace color="info">
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

export default UserAssessmentUpdate;
