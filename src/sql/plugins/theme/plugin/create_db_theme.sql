
--
-- Table structure for table core_theme
--
DROP TABLE IF EXISTS theme_theme;
CREATE TABLE  theme_theme (
  code_theme varchar(25) default '' NOT NULL,
  theme_description varchar(255),
  path_images varchar(255) NOT NULL,
  path_css varchar(255) NOT NULL,
  theme_author varchar(255),
  theme_author_url varchar(255),
  theme_version varchar(255) NOT NULL,
  theme_licence varchar(255) NOT NULL,
  path_js varchar(255) NOT NULL,
  PRIMARY KEY (code_theme)
);


--
-- Table structure for table core_theme_global
--
DROP TABLE IF EXISTS theme_global;
CREATE TABLE theme_global (
  global_theme_code varchar(50) default '' NOT NULL,
  PRIMARY KEY  (global_theme_code)
);
