import {
  entityTableSelector,
  entityDetailsButtonSelector,
  entityDetailsBackButtonSelector,
  entityCreateButtonSelector,
  entityCreateSaveButtonSelector,
  entityCreateCancelButtonSelector,
  entityEditButtonSelector,
  entityDeleteButtonSelector,
  entityConfirmDeleteButtonSelector,
} from '../../support/entity';

describe('UserAssessment e2e test', () => {
  const userAssessmentPageUrl = '/user-assessment';
  const userAssessmentPageUrlPattern = new RegExp('/user-assessment(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const userAssessmentSample = { status: 'ASSIGNED', assignedAt: '2024-03-16T17:04:39.142Z' };

  let userAssessment;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/user-assessments+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/user-assessments').as('postEntityRequest');
    cy.intercept('DELETE', '/api/user-assessments/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (userAssessment) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/user-assessments/${userAssessment.id}`,
      }).then(() => {
        userAssessment = undefined;
      });
    }
  });

  it('UserAssessments menu should load UserAssessments page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('user-assessment');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('UserAssessment').should('exist');
    cy.url().should('match', userAssessmentPageUrlPattern);
  });

  describe('UserAssessment page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(userAssessmentPageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create UserAssessment page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/user-assessment/new$'));
        cy.getEntityCreateUpdateHeading('UserAssessment');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', userAssessmentPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/user-assessments',
          body: userAssessmentSample,
        }).then(({ body }) => {
          userAssessment = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/user-assessments+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              headers: {
                link: '<http://localhost/api/user-assessments?page=0&size=20>; rel="last",<http://localhost/api/user-assessments?page=0&size=20>; rel="first"',
              },
              body: [userAssessment],
            },
          ).as('entitiesRequestInternal');
        });

        cy.visit(userAssessmentPageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details UserAssessment page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('userAssessment');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', userAssessmentPageUrlPattern);
      });

      it('edit button click should load edit UserAssessment page and go back', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('UserAssessment');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', userAssessmentPageUrlPattern);
      });

      it('edit button click should load edit UserAssessment page and save', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('UserAssessment');
        cy.get(entityCreateSaveButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', userAssessmentPageUrlPattern);
      });

      it('last delete button click should delete instance of UserAssessment', () => {
        cy.intercept('GET', '/api/user-assessments/*').as('dialogDeleteRequest');
        cy.get(entityDeleteButtonSelector).last().click();
        cy.wait('@dialogDeleteRequest');
        cy.getEntityDeleteDialogHeading('userAssessment').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', userAssessmentPageUrlPattern);

        userAssessment = undefined;
      });
    });
  });

  describe('new UserAssessment page', () => {
    beforeEach(() => {
      cy.visit(`${userAssessmentPageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('UserAssessment');
    });

    it('should create an instance of UserAssessment', () => {
      cy.get(`[data-cy="status"]`).select('COMPLETED');

      cy.get(`[data-cy="assignedAt"]`).type('2024-03-16T08:01');
      cy.get(`[data-cy="assignedAt"]`).blur();
      cy.get(`[data-cy="assignedAt"]`).should('have.value', '2024-03-16T08:01');

      cy.get(`[data-cy="deadline"]`).type('2024-03-17T00:03');
      cy.get(`[data-cy="deadline"]`).blur();
      cy.get(`[data-cy="deadline"]`).should('have.value', '2024-03-17T00:03');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(201);
        userAssessment = response.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(200);
      });
      cy.url().should('match', userAssessmentPageUrlPattern);
    });
  });
});
