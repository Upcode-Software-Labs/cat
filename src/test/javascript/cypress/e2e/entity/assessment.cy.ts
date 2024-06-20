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

describe('Assessment e2e test', () => {
  const assessmentPageUrl = '/assignment';
  const assessmentPageUrlPattern = new RegExp('/assignment(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const assessmentSample = {
    title: 'restfully profitable ick',
    type: 'atop abaft',
    validationCriteria: 'but concerning',
    question: 'even smoothly',
    maxPoints: 9509,
    deadline: '2024-03-16T15:29:59.122Z',
  };

  let assignment;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/assessments+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/assessments').as('postEntityRequest');
    cy.intercept('DELETE', '/api/assessments/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (assignment) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/assessments/${assignment.id}`,
      }).then(() => {
        assignment = undefined;
      });
    }
  });

  it('Assessments menu should load Assessments page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('assignment');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('Assessment').should('exist');
    cy.url().should('match', assessmentPageUrlPattern);
  });

  describe('Assessment page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(assessmentPageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create Assessment page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/assignment/new$'));
        cy.getEntityCreateUpdateHeading('Assessment');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', assessmentPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/assessments',
          body: assessmentSample,
        }).then(({ body }) => {
          assignment = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/assessments+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              headers: {
                link: '<http://localhost/api/assessments?page=0&size=20>; rel="last",<http://localhost/api/assessments?page=0&size=20>; rel="first"',
              },
              body: [assignment],
            },
          ).as('entitiesRequestInternal');
        });

        cy.visit(assessmentPageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details Assessment page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('assignment');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', assessmentPageUrlPattern);
      });

      it('edit button click should load edit Assessment page and go back', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('Assessment');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', assessmentPageUrlPattern);
      });

      it('edit button click should load edit Assessment page and save', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('Assessment');
        cy.get(entityCreateSaveButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', assessmentPageUrlPattern);
      });

      it('last delete button click should delete instance of Assessment', () => {
        cy.intercept('GET', '/api/assessments/*').as('dialogDeleteRequest');
        cy.get(entityDeleteButtonSelector).last().click();
        cy.wait('@dialogDeleteRequest');
        cy.getEntityDeleteDialogHeading('assignment').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', assessmentPageUrlPattern);

        assignment = undefined;
      });
    });
  });

  describe('new Assessment page', () => {
    beforeEach(() => {
      cy.visit(`${assessmentPageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('Assessment');
    });

    it('should create an instance of Assessment', () => {
      cy.get(`[data-cy="title"]`).type('lovingly under');
      cy.get(`[data-cy="title"]`).should('have.value', 'lovingly under');

      cy.get(`[data-cy="description"]`).type('../fake-data/blob/hipster.txt');
      cy.get(`[data-cy="description"]`).invoke('val').should('match', new RegExp('../fake-data/blob/hipster.txt'));

      cy.get(`[data-cy="languageFramework"]`).type('boo');
      cy.get(`[data-cy="languageFramework"]`).should('have.value', 'boo');

      cy.get(`[data-cy="difficultyLevel"]`).type('chateau');
      cy.get(`[data-cy="difficultyLevel"]`).should('have.value', 'chateau');

      cy.get(`[data-cy="timeLimit"]`).type('25814');
      cy.get(`[data-cy="timeLimit"]`).should('have.value', '25814');

      cy.get(`[data-cy="type"]`).type('tremendously');
      cy.get(`[data-cy="type"]`).should('have.value', 'tremendously');

      cy.get(`[data-cy="validationCriteria"]`).type('quixotic floodlight anti');
      cy.get(`[data-cy="validationCriteria"]`).should('have.value', 'quixotic floodlight anti');

      cy.get(`[data-cy="question"]`).type('efficiency yuck windsurf');
      cy.get(`[data-cy="question"]`).should('have.value', 'efficiency yuck windsurf');

      cy.get(`[data-cy="maxPoints"]`).type('23636');
      cy.get(`[data-cy="maxPoints"]`).should('have.value', '23636');

      cy.get(`[data-cy="deadline"]`).type('2024-03-16T11:19');
      cy.get(`[data-cy="deadline"]`).blur();
      cy.get(`[data-cy="deadline"]`).should('have.value', '2024-03-16T11:19');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(201);
        assignment = response.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(200);
      });
      cy.url().should('match', assessmentPageUrlPattern);
    });
  });
});
