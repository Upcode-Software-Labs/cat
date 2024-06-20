import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { openFile, byteSize, Translate, getPaginationState, JhiPagination, JhiItemCount } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faSort, faSortUp, faSortDown } from '@fortawesome/free-solid-svg-icons';
import { ASC, DESC, ITEMS_PER_PAGE, SORT } from 'app/shared/util/pagination.constants';
import { overridePaginationStateWithQueryParams } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntities } from './submission.reducer';

export const Submission = () => {
  const dispatch = useAppDispatch();

  const pageLocation = useLocation();
  const navigate = useNavigate();

  const [paginationState, setPaginationState] = useState(
    overridePaginationStateWithQueryParams(getPaginationState(pageLocation, ITEMS_PER_PAGE, 'id'), pageLocation.search),
  );

  const submissionList = useAppSelector(state => state.submission.entities);
  const loading = useAppSelector(state => state.submission.loading);
  const totalItems = useAppSelector(state => state.submission.totalItems);

  const getAllEntities = () => {
    dispatch(
      getEntities({
        page: paginationState.activePage - 1,
        size: paginationState.itemsPerPage,
        sort: `${paginationState.sort},${paginationState.order}`,
      }),
    );
  };

  const sortEntities = () => {
    getAllEntities();
    const endURL = `?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`;
    if (pageLocation.search !== endURL) {
      navigate(`${pageLocation.pathname}${endURL}`);
    }
  };

  useEffect(() => {
    sortEntities();
  }, [paginationState.activePage, paginationState.order, paginationState.sort]);

  useEffect(() => {
    const params = new URLSearchParams(pageLocation.search);
    const page = params.get('page');
    const sort = params.get(SORT);
    if (page && sort) {
      const sortSplit = sort.split(',');
      setPaginationState({
        ...paginationState,
        activePage: +page,
        sort: sortSplit[0],
        order: sortSplit[1],
      });
    }
  }, [pageLocation.search]);

  const sort = p => () => {
    setPaginationState({
      ...paginationState,
      order: paginationState.order === ASC ? DESC : ASC,
      sort: p,
    });
  };

  const handlePagination = currentPage =>
    setPaginationState({
      ...paginationState,
      activePage: currentPage,
    });

  const handleSyncList = () => {
    sortEntities();
  };

  const getSortIconByFieldName = (fieldName: string) => {
    const sortFieldName = paginationState.sort;
    const order = paginationState.order;
    if (sortFieldName !== fieldName) {
      return faSort;
    } else {
      return order === ASC ? faSortUp : faSortDown;
    }
  };

  return (
    <div>
      <h2 id="submission-heading" data-cy="SubmissionHeading">
        <Translate contentKey="catApp.submission.home.title">Submissions!!</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="catApp.submission.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to="/submission/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="catApp.submission.home.createLabel">Create new Submission</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {submissionList && submissionList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  <Translate contentKey="catApp.submission.id">ID</Translate> <FontAwesomeIcon icon={getSortIconByFieldName('id')} />
                </th>
                <th className="hand" onClick={sort('githubUrl')}>
                  <Translate contentKey="catApp.submission.githubUrl">Github Url</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('githubUrl')} />
                </th>
                <th className="hand" onClick={sort('screenshots')}>
                  <Translate contentKey="catApp.submission.screenshots">Screenshots</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('screenshots')} />
                </th>
                <th className="hand" onClick={sort('videoExplanation')}>
                  <Translate contentKey="catApp.submission.videoExplanation">Video Explanation</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('videoExplanation')} />
                </th>
                <th className="hand" onClick={sort('textDescription')}>
                  <Translate contentKey="catApp.submission.textDescription">Text Description</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('textDescription')} />
                </th>
                <th className="hand" onClick={sort('feedback')}>
                  <Translate contentKey="catApp.submission.feedback">Feedback</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('feedback')} />
                </th>
                <th className="hand" onClick={sort('pointsScored')}>
                  <Translate contentKey="catApp.submission.pointsScored">Points Scored</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('pointsScored')} />
                </th>
                <th>
                  <Translate contentKey="catApp.submission.forAssignment">For Assignment</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  <Translate contentKey="catApp.submission.user">User</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  <Translate contentKey="catApp.submission.assignment">Assessment</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {submissionList.map((submission, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/submission/${submission.id}`} color="link" size="sm">
                      {submission.id}
                    </Button>
                  </td>
                  <td>{submission.githubUrl}</td>
                  <td>
                    {submission.screenshots ? (
                      <div>
                        {submission.screenshotsContentType ? (
                          <a onClick={openFile(submission.screenshotsContentType, submission.screenshots)}>
                            <img
                              src={`data:${submission.screenshotsContentType};base64,${submission.screenshots}`}
                              style={{ maxHeight: '30px' }}
                            />
                            &nbsp;
                          </a>
                        ) : null}
                        <span>
                          {submission.screenshotsContentType}, {byteSize(submission.screenshots)}
                        </span>
                      </div>
                    ) : null}
                  </td>
                  <td>{submission.videoExplanation}</td>
                  <td>{submission.textDescription}</td>
                  <td>{submission.feedback}</td>
                  <td>{submission.pointsScored}</td>
                  <td>
                    {submission.forAssignment ? (
                      <Link to={`/user-assignment/${submission.forAssignment.id}`}>{submission.forAssignment.id}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td>{submission.user ? submission.user.id : ''}</td>
                  <td>
                    {submission.assignment ? <Link to={`/assignment/${submission.assignment.id}`}>{submission.assignment.id}</Link> : ''}
                  </td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/submission/${submission.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/submission/${submission.id}/edit?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
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
                        onClick={() =>
                          (window.location.href = `/submission/${submission.id}/delete?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`)
                        }
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
              <Translate contentKey="catApp.submission.home.notFound">No Submissions found</Translate>
            </div>
          )
        )}
      </div>
      {totalItems ? (
        <div className={submissionList && submissionList.length > 0 ? '' : 'd-none'}>
          <div className="justify-content-center d-flex">
            <JhiItemCount page={paginationState.activePage} total={totalItems} itemsPerPage={paginationState.itemsPerPage} i18nEnabled />
          </div>
          <div className="justify-content-center d-flex">
            <JhiPagination
              activePage={paginationState.activePage}
              onSelect={handlePagination}
              maxButtons={5}
              itemsPerPage={paginationState.itemsPerPage}
              totalItems={totalItems}
            />
          </div>
        </div>
      ) : (
        ''
      )}
    </div>
  );
};

export default Submission;
