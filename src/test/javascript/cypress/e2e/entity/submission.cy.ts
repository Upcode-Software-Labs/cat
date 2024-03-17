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

describe('Submission e2e test', () => {
  const submissionPageUrl = '/submission';
  const submissionPageUrlPattern = new RegExp('/submission(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const submissionSample = { githubUrl: 'mysteriously excepting unabashedly' };

  let submission;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/submissions+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/submissions').as('postEntityRequest');
    cy.intercept('DELETE', '/api/submissions/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (submission) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/submissions/${submission.id}`,
      }).then(() => {
        submission = undefined;
      });
    }
  });

  it('Submissions menu should load Submissions page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('submission');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('Submission').should('exist');
    cy.url().should('match', submissionPageUrlPattern);
  });

  describe('Submission page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(submissionPageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create Submission page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/submission/new$'));
        cy.getEntityCreateUpdateHeading('Submission');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', submissionPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/submissions',
          body: submissionSample,
        }).then(({ body }) => {
          submission = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/submissions+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              headers: {
                link: '<http://localhost/api/submissions?page=0&size=20>; rel="last",<http://localhost/api/submissions?page=0&size=20>; rel="first"',
              },
              body: [submission],
            },
          ).as('entitiesRequestInternal');
        });

        cy.visit(submissionPageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details Submission page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('submission');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', submissionPageUrlPattern);
      });

      it('edit button click should load edit Submission page and go back', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('Submission');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', submissionPageUrlPattern);
      });

      it('edit button click should load edit Submission page and save', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('Submission');
        cy.get(entityCreateSaveButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', submissionPageUrlPattern);
      });

      it('last delete button click should delete instance of Submission', () => {
        cy.intercept('GET', '/api/submissions/*').as('dialogDeleteRequest');
        cy.get(entityDeleteButtonSelector).last().click();
        cy.wait('@dialogDeleteRequest');
        cy.getEntityDeleteDialogHeading('submission').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', submissionPageUrlPattern);

        submission = undefined;
      });
    });
  });

  describe('new Submission page', () => {
    beforeEach(() => {
      cy.visit(`${submissionPageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('Submission');
    });

    it('should create an instance of Submission', () => {
      cy.get(`[data-cy="githubUrl"]`).type('badly rubbery urgently');
      cy.get(`[data-cy="githubUrl"]`).should('have.value', 'badly rubbery urgently');

      cy.setFieldImageAsBytesOfEntity('screenshots', 'integration-test.png', 'image/png');

      cy.get(`[data-cy="videoExplanation"]`).type('drat');
      cy.get(`[data-cy="videoExplanation"]`).should('have.value', 'drat');

      cy.get(`[data-cy="textDescription"]`).type('violet substantial flutter');
      cy.get(`[data-cy="textDescription"]`).should('have.value', 'violet substantial flutter');

      cy.get(`[data-cy="feedback"]`).type('../fake-data/blob/hipster.txt');
      cy.get(`[data-cy="feedback"]`).invoke('val').should('match', new RegExp('../fake-data/blob/hipster.txt'));

      cy.get(`[data-cy="pointsScored"]`).type('8837');
      cy.get(`[data-cy="pointsScored"]`).should('have.value', '8837');

      // since cypress clicks submit too fast before the blob fields are validated
      cy.wait(200); // eslint-disable-line cypress/no-unnecessary-waiting
      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(201);
        submission = response.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(200);
      });
      cy.url().should('match', submissionPageUrlPattern);
    });
  });
});
