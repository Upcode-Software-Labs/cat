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

describe('ValidationRule e2e test', () => {
  const validationRulePageUrl = '/validation-rule';
  const validationRulePageUrlPattern = new RegExp('/validation-rule(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const validationRuleSample = { description: 'Li4vZmFrZS1kYXRhL2Jsb2IvaGlwc3Rlci50eHQ=', ruleType: 'rigidly boohoo compassionate' };

  let validationRule;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/validation-rules+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/validation-rules').as('postEntityRequest');
    cy.intercept('DELETE', '/api/validation-rules/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (validationRule) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/validation-rules/${validationRule.id}`,
      }).then(() => {
        validationRule = undefined;
      });
    }
  });

  it('ValidationRules menu should load ValidationRules page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('validation-rule');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('ValidationRule').should('exist');
    cy.url().should('match', validationRulePageUrlPattern);
  });

  describe('ValidationRule page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(validationRulePageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create ValidationRule page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/validation-rule/new$'));
        cy.getEntityCreateUpdateHeading('ValidationRule');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', validationRulePageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/validation-rules',
          body: validationRuleSample,
        }).then(({ body }) => {
          validationRule = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/validation-rules+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              body: [validationRule],
            },
          ).as('entitiesRequestInternal');
        });

        cy.visit(validationRulePageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details ValidationRule page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('validationRule');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', validationRulePageUrlPattern);
      });

      it('edit button click should load edit ValidationRule page and go back', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('ValidationRule');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', validationRulePageUrlPattern);
      });

      it('edit button click should load edit ValidationRule page and save', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('ValidationRule');
        cy.get(entityCreateSaveButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', validationRulePageUrlPattern);
      });

      it('last delete button click should delete instance of ValidationRule', () => {
        cy.intercept('GET', '/api/validation-rules/*').as('dialogDeleteRequest');
        cy.get(entityDeleteButtonSelector).last().click();
        cy.wait('@dialogDeleteRequest');
        cy.getEntityDeleteDialogHeading('validationRule').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', validationRulePageUrlPattern);

        validationRule = undefined;
      });
    });
  });

  describe('new ValidationRule page', () => {
    beforeEach(() => {
      cy.visit(`${validationRulePageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('ValidationRule');
    });

    it('should create an instance of ValidationRule', () => {
      cy.get(`[data-cy="description"]`).type('../fake-data/blob/hipster.txt');
      cy.get(`[data-cy="description"]`).invoke('val').should('match', new RegExp('../fake-data/blob/hipster.txt'));

      cy.get(`[data-cy="validationScript"]`).type('../fake-data/blob/hipster.txt');
      cy.get(`[data-cy="validationScript"]`).invoke('val').should('match', new RegExp('../fake-data/blob/hipster.txt'));

      cy.get(`[data-cy="ruleType"]`).type('astride blah brr');
      cy.get(`[data-cy="ruleType"]`).should('have.value', 'astride blah brr');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(201);
        validationRule = response.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(200);
      });
      cy.url().should('match', validationRulePageUrlPattern);
    });
  });
});
