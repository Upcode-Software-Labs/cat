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

describe('SubmissionResult e2e test', () => {
  const submissionResultPageUrl = '/submission-result';
  const submissionResultPageUrlPattern = new RegExp('/submission-result(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const submissionResultSample = {};

  let submissionResult;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/submission-results+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/submission-results').as('postEntityRequest');
    cy.intercept('DELETE', '/api/submission-results/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (submissionResult) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/submission-results/${submissionResult.id}`,
      }).then(() => {
        submissionResult = undefined;
      });
    }
  });

  it('SubmissionResults menu should load SubmissionResults page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('submission-result');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('SubmissionResult').should('exist');
    cy.url().should('match', submissionResultPageUrlPattern);
  });

  describe('SubmissionResult page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(submissionResultPageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create SubmissionResult page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/submission-result/new$'));
        cy.getEntityCreateUpdateHeading('SubmissionResult');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', submissionResultPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/submission-results',
          body: submissionResultSample,
        }).then(({ body }) => {
          submissionResult = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/submission-results+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              body: [submissionResult],
            },
          ).as('entitiesRequestInternal');
        });

        cy.visit(submissionResultPageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details SubmissionResult page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('submissionResult');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', submissionResultPageUrlPattern);
      });

      it('edit button click should load edit SubmissionResult page and go back', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('SubmissionResult');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', submissionResultPageUrlPattern);
      });

      it('edit button click should load edit SubmissionResult page and save', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('SubmissionResult');
        cy.get(entityCreateSaveButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', submissionResultPageUrlPattern);
      });

      it('last delete button click should delete instance of SubmissionResult', () => {
        cy.intercept('GET', '/api/submission-results/*').as('dialogDeleteRequest');
        cy.get(entityDeleteButtonSelector).last().click();
        cy.wait('@dialogDeleteRequest');
        cy.getEntityDeleteDialogHeading('submissionResult').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', submissionResultPageUrlPattern);

        submissionResult = undefined;
      });
    });
  });

  describe('new SubmissionResult page', () => {
    beforeEach(() => {
      cy.visit(`${submissionResultPageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('SubmissionResult');
    });

    it('should create an instance of SubmissionResult', () => {
      cy.get(`[data-cy="totalPoints"]`).type('7493');
      cy.get(`[data-cy="totalPoints"]`).should('have.value', '7493');

      cy.get(`[data-cy="detailedResults"]`).type('../fake-data/blob/hipster.txt');
      cy.get(`[data-cy="detailedResults"]`).invoke('val').should('match', new RegExp('../fake-data/blob/hipster.txt'));

      cy.get(`[data-cy="feedback"]`).type('../fake-data/blob/hipster.txt');
      cy.get(`[data-cy="feedback"]`).invoke('val').should('match', new RegExp('../fake-data/blob/hipster.txt'));

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(201);
        submissionResult = response.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(200);
      });
      cy.url().should('match', submissionResultPageUrlPattern);
    });
  });
});
