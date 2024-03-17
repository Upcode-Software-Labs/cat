import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { byteSize, Translate, TextFormat, getPaginationState, JhiPagination, JhiItemCount } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faSort, faSortUp, faSortDown } from '@fortawesome/free-solid-svg-icons';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { ASC, DESC, ITEMS_PER_PAGE, SORT } from 'app/shared/util/pagination.constants';
import { overridePaginationStateWithQueryParams } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntities } from './assessment.reducer';

export const Assessment = () => {
  const dispatch = useAppDispatch();

  const pageLocation = useLocation();
  const navigate = useNavigate();

  const [paginationState, setPaginationState] = useState(
    overridePaginationStateWithQueryParams(getPaginationState(pageLocation, ITEMS_PER_PAGE, 'id'), pageLocation.search),
  );

  const assessmentList = useAppSelector(state => state.assessment.entities);
  const loading = useAppSelector(state => state.assessment.loading);
  const totalItems = useAppSelector(state => state.assessment.totalItems);

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
      <h2 id="assessment-heading" data-cy="AssessmentHeading">
        <Translate contentKey="catApp.assessment.home.title">Assessments</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="catApp.assessment.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to="/assessment/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="catApp.assessment.home.createLabel">Create new Assessment</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {assessmentList && assessmentList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  <Translate contentKey="catApp.assessment.id">ID</Translate> <FontAwesomeIcon icon={getSortIconByFieldName('id')} />
                </th>
                <th className="hand" onClick={sort('title')}>
                  <Translate contentKey="catApp.assessment.title">Title</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('title')} />
                </th>
                <th className="hand" onClick={sort('description')}>
                  <Translate contentKey="catApp.assessment.description">Description</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('description')} />
                </th>
                <th className="hand" onClick={sort('languageFramework')}>
                  <Translate contentKey="catApp.assessment.languageFramework">Language Framework</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('languageFramework')} />
                </th>
                <th className="hand" onClick={sort('difficultyLevel')}>
                  <Translate contentKey="catApp.assessment.difficultyLevel">Difficulty Level</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('difficultyLevel')} />
                </th>
                <th className="hand" onClick={sort('timeLimit')}>
                  <Translate contentKey="catApp.assessment.timeLimit">Time Limit</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('timeLimit')} />
                </th>
                <th className="hand" onClick={sort('type')}>
                  <Translate contentKey="catApp.assessment.type">Type</Translate> <FontAwesomeIcon icon={getSortIconByFieldName('type')} />
                </th>
                <th className="hand" onClick={sort('validationCriteria')}>
                  <Translate contentKey="catApp.assessment.validationCriteria">Validation Criteria</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('validationCriteria')} />
                </th>
                <th className="hand" onClick={sort('question')}>
                  <Translate contentKey="catApp.assessment.question">Question</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('question')} />
                </th>
                <th className="hand" onClick={sort('maxPoints')}>
                  <Translate contentKey="catApp.assessment.maxPoints">Max Points</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('maxPoints')} />
                </th>
                <th className="hand" onClick={sort('deadline')}>
                  <Translate contentKey="catApp.assessment.deadline">Deadline</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('deadline')} />
                </th>
                <th>
                  <Translate contentKey="catApp.assessment.assignedToUser">Assigned To User</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {assessmentList.map((assessment, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/assessment/${assessment.id}`} color="link" size="sm">
                      {assessment.id}
                    </Button>
                  </td>
                  <td>{assessment.title}</td>
                  <td>{assessment.description}</td>
                  <td>{assessment.languageFramework}</td>
                  <td>{assessment.difficultyLevel}</td>
                  <td>{assessment.timeLimit}</td>
                  <td>{assessment.type}</td>
                  <td>{assessment.validationCriteria}</td>
                  <td>{assessment.question}</td>
                  <td>{assessment.maxPoints}</td>
                  <td>{assessment.deadline ? <TextFormat type="date" value={assessment.deadline} format={APP_DATE_FORMAT} /> : null}</td>
                  <td>{assessment.assignedToUser ? assessment.assignedToUser.id : ''}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/assessment/${assessment.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/assessment/${assessment.id}/edit?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
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
                          (window.location.href = `/assessment/${assessment.id}/delete?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`)
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
              <Translate contentKey="catApp.assessment.home.notFound">No Assessments found</Translate>
            </div>
          )
        )}
      </div>
      {totalItems ? (
        <div className={assessmentList && assessmentList.length > 0 ? '' : 'd-none'}>
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

export default Assessment;
