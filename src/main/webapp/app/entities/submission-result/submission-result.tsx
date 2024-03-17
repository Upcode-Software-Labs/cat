import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { byteSize, Translate, getSortState } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faSort, faSortUp, faSortDown } from '@fortawesome/free-solid-svg-icons';
import { ASC, DESC, SORT } from 'app/shared/util/pagination.constants';
import { overrideSortStateWithQueryParams } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntities } from './submission-result.reducer';

export const SubmissionResult = () => {
  const dispatch = useAppDispatch();

  const pageLocation = useLocation();
  const navigate = useNavigate();

  const [sortState, setSortState] = useState(overrideSortStateWithQueryParams(getSortState(pageLocation, 'id'), pageLocation.search));

  const submissionResultList = useAppSelector(state => state.submissionResult.entities);
  const loading = useAppSelector(state => state.submissionResult.loading);

  const getAllEntities = () => {
    dispatch(
      getEntities({
        sort: `${sortState.sort},${sortState.order}`,
      }),
    );
  };

  const sortEntities = () => {
    getAllEntities();
    const endURL = `?sort=${sortState.sort},${sortState.order}`;
    if (pageLocation.search !== endURL) {
      navigate(`${pageLocation.pathname}${endURL}`);
    }
  };

  useEffect(() => {
    sortEntities();
  }, [sortState.order, sortState.sort]);

  const sort = p => () => {
    setSortState({
      ...sortState,
      order: sortState.order === ASC ? DESC : ASC,
      sort: p,
    });
  };

  const handleSyncList = () => {
    sortEntities();
  };

  const getSortIconByFieldName = (fieldName: string) => {
    const sortFieldName = sortState.sort;
    const order = sortState.order;
    if (sortFieldName !== fieldName) {
      return faSort;
    } else {
      return order === ASC ? faSortUp : faSortDown;
    }
  };

  return (
    <div>
      <h2 id="submission-result-heading" data-cy="SubmissionResultHeading">
        <Translate contentKey="catApp.submissionResult.home.title">Submission Results</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="catApp.submissionResult.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to="/submission-result/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="catApp.submissionResult.home.createLabel">Create new Submission Result</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {submissionResultList && submissionResultList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  <Translate contentKey="catApp.submissionResult.id">ID</Translate> <FontAwesomeIcon icon={getSortIconByFieldName('id')} />
                </th>
                <th className="hand" onClick={sort('totalPoints')}>
                  <Translate contentKey="catApp.submissionResult.totalPoints">Total Points</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('totalPoints')} />
                </th>
                <th className="hand" onClick={sort('detailedResults')}>
                  <Translate contentKey="catApp.submissionResult.detailedResults">Detailed Results</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('detailedResults')} />
                </th>
                <th className="hand" onClick={sort('feedback')}>
                  <Translate contentKey="catApp.submissionResult.feedback">Feedback</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('feedback')} />
                </th>
                <th>
                  <Translate contentKey="catApp.submissionResult.submission">Submission</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {submissionResultList.map((submissionResult, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/submission-result/${submissionResult.id}`} color="link" size="sm">
                      {submissionResult.id}
                    </Button>
                  </td>
                  <td>{submissionResult.totalPoints}</td>
                  <td>{submissionResult.detailedResults}</td>
                  <td>{submissionResult.feedback}</td>
                  <td>
                    {submissionResult.submission ? (
                      <Link to={`/submission/${submissionResult.submission.id}`}>{submissionResult.submission.id}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button
                        tag={Link}
                        to={`/submission-result/${submissionResult.id}`}
                        color="info"
                        size="sm"
                        data-cy="entityDetailsButton"
                      >
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/submission-result/${submissionResult.id}/edit`}
                        color="primary"
                        size="sm"
                        data-cy="entityEditButton"
                      >
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button
                        onClick={() => (window.location.href = `/submission-result/${submissionResult.id}/delete`)}
                        color="danger"
                        size="sm"
                        data-cy="entityDeleteButton"
                      >
                        <FontAwesomeIcon icon="trash" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.delete">Delete</Translate>
                        </span>
                      </Button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </Table>
        ) : (
          !loading && (
            <div className="alert alert-warning">
              <Translate contentKey="catApp.submissionResult.home.notFound">No Submission Results found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default SubmissionResult;
