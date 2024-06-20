import React from 'react';
import { Translate } from 'react-jhipster';

import MenuItem from 'app/shared/layout/menus/menu-item';

const EntitiesMenu = () => {
  return (
    <>
      {/* prettier-ignore */}
      <MenuItem icon="asterisk" to="/assignment">
        <Translate contentKey="global.menu.entities.assignment" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/user-assignment">
        <Translate contentKey="global.menu.entities.userAssignment" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/submission">
        <Translate contentKey="global.menu.entities.submission" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/question">
        <Translate contentKey="global.menu.entities.question" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/validation-rule">
        <Translate contentKey="global.menu.entities.validationRule" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/submission-result">
        <Translate contentKey="global.menu.entities.submissionResult" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/audit-log">
        <Translate contentKey="global.menu.entities.auditLog" />
      </MenuItem>
      {/* jhipster-needle-add-entity-to-menu - JHipster will add entities to the menu here */}
    </>
  );
};

export default EntitiesMenu;
