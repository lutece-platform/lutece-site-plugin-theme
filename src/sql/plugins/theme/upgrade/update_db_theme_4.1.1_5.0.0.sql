DROP TABLE IF EXISTS core_theme_global;
UPDATE core_admin_right SET icon_url='ti ti-brush' WHERE id_right='THEME_MANAGEMENT';
INSERT INTO core_datastore VALUES ('theme.globalThemeCode','lutece');
INSERT INTO core_datastore VALUES ('theme.globalThemeVersion','1.0');