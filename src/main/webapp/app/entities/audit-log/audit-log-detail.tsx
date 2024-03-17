import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, byteSize, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './audit-log.reducer';

export const AuditLogDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const auditLogEntity = useAppSelector(state => state.auditLog.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="auditLogDetailsHeading">
          <Translate contentKey="catApp.auditLog.detail.title">AuditLog</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{auditLogEntity.id}</dd>
          <dt>
            <span id="action">
              <Translate contentKey="catApp.auditLog.action">Action</Translate>
            </span>
          </dt>
          <dd>{auditLogEntity.action}</dd>
          <dt>
            <span id="performedAt">
              <Translate contentKey="catApp.auditLog.performedAt">Performed At</Translate>
            </span>
          </dt>
          <dd>
            {auditLogEntity.performedAt ? <TextFormat value={auditLogEntity.performedAt} type="date" format={APP_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="details">
              <Translate contentKey="catApp.auditLog.details">Details</Translate>
            </span>
          </dt>
          <dd>{auditLogEntity.details}</dd>
          <dt>
            <Translate contentKey="catApp.auditLog.user">User</Translate>
          </dt>
          <dd>{auditLogEntity.user ? auditLogEntity.user.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/audit-log" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/audit-log/${auditLogEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default AuditLogDetail;
