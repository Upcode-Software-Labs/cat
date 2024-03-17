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
import { getEntity, updateEntity, createEntity, reset } from './assessment.reducer';

export const AssessmentUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const users = useAppSelector(state => state.userManagement.users);
  const assessmentEntity = useAppSelector(state => state.assessment.entity);
  const loading = useAppSelector(state => state.assessment.loading);
  const updating = useAppSelector(state => state.assessment.updating);
  const updateSuccess = useAppSelector(state => state.assessment.updateSuccess);

  const handleClose = () => {
    navigate('/assessment' + location.search);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getUsers({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  // eslint-disable-next-line complexity
  const saveEntity = values => {
    if (values.timeLimit !== undefined && typeof values.timeLimit !== 'number') {
      values.timeLimit = Number(values.timeLimit);
    }
    if (values.maxPoints !== undefined && typeof values.maxPoints !== 'number') {
      values.maxPoints = Number(values.maxPoints);
    }
    values.deadline = convertDateTimeToServer(values.deadline);

    const entity = {
      ...assessmentEntity,
      ...values,
      assignedToUser: users.find(it => it.id.toString() === values.assignedToUser.toString()),
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
          deadline: displayDefaultDateTime(),
        }
      : {
          ...assessmentEntity,
          deadline: convertDateTimeFromServer(assessmentEntity.deadline),
          assignedToUser: assessmentEntity?.assignedToUser?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="catApp.assessment.home.createOrEditLabel" data-cy="AssessmentCreateUpdateHeading">
            <Translate contentKey="catApp.assessment.home.createOrEditLabel">Create or edit a Assessment</Translate>
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
                  id="assessment-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('catApp.assessment.title')}
                id="assessment-title"
                name="title"
                data-cy="title"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('catApp.assessment.description')}
                id="assessment-description"
                name="description"
                data-cy="description"
                type="textarea"
              />
              <ValidatedField
                label={translate('catApp.assessment.languageFramework')}
                id="assessment-languageFramework"
                name="languageFramework"
                data-cy="languageFramework"
                type="text"
              />
              <ValidatedField
                label={translate('catApp.assessment.difficultyLevel')}
                id="assessment-difficultyLevel"
                name="difficultyLevel"
                data-cy="difficultyLevel"
                type="text"
              />
              <ValidatedField
                label={translate('catApp.assessment.timeLimit')}
                id="assessment-timeLimit"
                name="timeLimit"
                data-cy="timeLimit"
                type="text"
              />
              <ValidatedField
                label={translate('catApp.assessment.type')}
                id="assessment-type"
                name="type"
                data-cy="type"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('catApp.assessment.validationCriteria')}
                id="assessment-validationCriteria"
                name="validationCriteria"
                data-cy="validationCriteria"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('catApp.assessment.question')}
                id="assessment-question"
                name="question"
                data-cy="question"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('catApp.assessment.maxPoints')}
                id="assessment-maxPoints"
                name="maxPoints"
                data-cy="maxPoints"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  validate: v => isNumber(v) || translate('entity.validation.number'),
                }}
              />
              <ValidatedField
                label={translate('catApp.assessment.deadline')}
                id="assessment-deadline"
                name="deadline"
                data-cy="deadline"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                id="assessment-assignedToUser"
                name="assignedToUser"
                data-cy="assignedToUser"
                label={translate('catApp.assessment.assignedToUser')}
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
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/assessment" replace color="info">
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

export default AssessmentUpdate;
