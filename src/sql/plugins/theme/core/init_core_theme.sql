--
-- Init  table core_admin_right
--
INSERT INTO core_admin_right (id_right,name,level_right,admin_url,description,is_updatable,plugin_name,id_feature_group,icon_url, documentation_url) VALUES 
('THEME_MANAGEMENT','theme.adminFeature.theme_management.name',0,'jsp/admin/plugins/theme/ManageThemes.jsp','theme.adminFeature.theme_management.description',1,'theme','STYLE','images/admin/skin/features/manage_styles.png', NULL);

--
-- Init  table core_user_right
--
INSERT INTO core_user_right (id_right,id_user) VALUES ('THEME_MANAGEMENT',1);

--
-- Init  table core_admin_role
--
INSERT INTO core_admin_role (role_key,role_description) VALUES ('theme_manager', 'Theme management');

--
-- Init  table core_admin_role_resource
--
INSERT INTO core_admin_role_resource (rbac_id,role_key,resource_type,resource_id,permission) VALUES (155,'theme_manager','THEME','*','*');

--
-- Init  table core_user_role
--
INSERT INTO core_user_role (role_key,id_user) VALUES ('theme_manager',1);
