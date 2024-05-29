import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IAssessment } from 'app/shared/model/assignment.model';
import { getEntities as getAssessments } from 'app/entities/assignment/assignment.reducer';
import { IValidationRule } from 'app/shared/model/validation-rule.model';
import { getEntity, updateEntity, createEntity, reset } from './validation-rule.reducer';

export const ValidationRuleUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const assessments = useAppSelector(state => state.assignment.entities);
  const validationRuleEntity = useAppSelector(state => state.validationRule.entity);
  const loading = useAppSelector(state => state.validationRule.loading);
  const updating = useAppSelector(state => state.validationRule.updating);
  const updateSuccess = useAppSelector(state => state.validationRule.updateSuccess);

  const handleClose = () => {
    navigate('/validation-rule');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getAssessments({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  // eslint-disable-next-line complexity
  const saveEntity = values => {
    const entity = {
      ...validationRuleEntity,
      ...values,
      assignment: assessments.find(it => it.id.toString() === values.assignment.toString()),
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
          ...validationRuleEntity,
          assignment: validationRuleEntity?.assignment?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="catApp.validationRule.home.createOrEditLabel" data-cy="ValidationRuleCreateUpdateHeading">
            <Translate contentKey="catApp.validationRule.home.createOrEditLabel">Create or edit a ValidationRule</Translate>
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
                  id="validation-rule-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('catApp.validationRule.description')}
                id="validation-rule-description"
                name="description"
                data-cy="description"
                type="textarea"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('catApp.validationRule.validationScript')}
                id="validation-rule-validationScript"
                name="validationScript"
                data-cy="validationScript"
                type="textarea"
              />
              <ValidatedField
                label={translate('catApp.validationRule.ruleType')}
                id="validation-rule-ruleType"
                name="ruleType"
                data-cy="ruleType"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                id="validation-rule-assignment"
                name="assignment"
                data-cy="assignment"
                label={translate('catApp.validationRule.assignment')}
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
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/validation-rule" replace color="info">
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

export default ValidationRuleUpdate;
