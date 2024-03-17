import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, byteSize } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './validation-rule.reducer';

export const ValidationRuleDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const validationRuleEntity = useAppSelector(state => state.validationRule.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="validationRuleDetailsHeading">
          <Translate contentKey="catApp.validationRule.detail.title">ValidationRule</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{validationRuleEntity.id}</dd>
          <dt>
            <span id="description">
              <Translate contentKey="catApp.validationRule.description">Description</Translate>
            </span>
          </dt>
          <dd>{validationRuleEntity.description}</dd>
          <dt>
            <span id="validationScript">
              <Translate contentKey="catApp.validationRule.validationScript">Validation Script</Translate>
            </span>
          </dt>
          <dd>{validationRuleEntity.validationScript}</dd>
          <dt>
            <span id="ruleType">
              <Translate contentKey="catApp.validationRule.ruleType">Rule Type</Translate>
            </span>
          </dt>
          <dd>{validationRuleEntity.ruleType}</dd>
          <dt>
            <Translate contentKey="catApp.validationRule.assessment">Assessment</Translate>
          </dt>
          <dd>{validationRuleEntity.assessment ? validationRuleEntity.assessment.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/validation-rule" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/validation-rule/${validationRuleEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default ValidationRuleDetail;
