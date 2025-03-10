/*
 * Copyright (c) 2002-2014, Mairie de Paris
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *  1. Redistributions of source code must retain the above copyright notice
 *     and the following disclaimer.
 *
 *  2. Redistributions in binary form must reproduce the above copyright notice
 *     and the following disclaimer in the documentation and/or other materials
 *     provided with the distribution.
 *
 *  3. Neither the name of 'Mairie de Paris' nor 'Lutece' nor the names of its
 *     contributors may be used to endorse or promote products derived from
 *     this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDERS OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 *
 * License 1.0
 */
package fr.paris.lutece.plugins.theme.business;

import fr.paris.lutece.portal.business.style.Theme;
import fr.paris.lutece.portal.service.plugin.Plugin;
import fr.paris.lutece.util.ReferenceList;
import fr.paris.lutece.util.sql.DAOUtil;

import java.util.ArrayList;
import java.util.Collection;


/**
 * This class provides Data Access methods for Theme objects
 */
public final class ThemeDAO implements IThemeDAO
{
    private static final String SQL_QUERY_SELECT = " SELECT code_theme, theme_description, path_images, path_css, theme_author, " +
        " theme_author_url, theme_version, theme_licence, path_js FROM theme_theme WHERE code_theme = ?";
    private static final String SQL_QUERY_SELECTALL = " SELECT code_theme, theme_description, path_images, path_css, theme_author, " +
        " theme_author_url, theme_version, theme_licence, path_js FROM theme_theme ORDER BY code_theme";
    private static final String SQL_QUERY_SELECT_THEME = " SELECT code_theme, theme_description FROM theme_theme";
    private static final String SQL_QUERY_SELECT_GLOBAL_THEME = " SELECT tt.code_theme, tt.theme_description, tt.path_images, tt.path_css, " +
        " tt.theme_author, tt.theme_author_url, tt.theme_version, tt.theme_licence, tt.path_js " +
        " FROM theme_theme tt WHERE tt.code_theme = ( SELECT entity_value FROM core_datastore WHERE entity_key='theme.globalthemecode' )";
    private static final String SQL_QUERY_UPDATE_GLOBAL_THEME = " UPDATE core_datastore SET entity_value = ? WHERE entity_key='theme.globalthemecode'";
    private static final String SQL_QUERY_UPDATE_GLOBAL_THEME_VERSION = " UPDATE core_datastore SET entity_value = ? WHERE entity_key='theme.globalThemeVersion'";
    
    /**
     * {@inheritDoc}
     */
    public Theme load( String strCodeTheme, Plugin plugin )
    {
        Theme theme = null;
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECT, plugin );
        daoUtil.setString( 1, strCodeTheme );

        daoUtil.executeQuery(  );

        if ( daoUtil.next(  ) )
        {
            theme = new Theme(  );
            theme.setCodeTheme( daoUtil.getString( 1 ) );
            theme.setThemeDescription( daoUtil.getString( 2 ) );
            theme.setPathImages( daoUtil.getString( 3 ) );
            theme.setPathCss( daoUtil.getString( 4 ) );
            theme.setThemeAuthor( daoUtil.getString( 5 ) );
            theme.setThemeAuthorUrl( daoUtil.getString( 6 ) );
            theme.setThemeVersion( daoUtil.getString( 7 ) );
            theme.setThemeLicence( daoUtil.getString( 8 ) );
            theme.setPathJs( daoUtil.getString( 9 ) );
        }

        daoUtil.free(  );

        return theme;
    }

    /**
     * {@inheritDoc}
     */
    public Collection<Theme> selectThemesList( Plugin plugin )
    {
        Collection<Theme> themeList = new ArrayList<Theme>(  );
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECTALL, plugin );
        daoUtil.executeQuery(  );

        while ( daoUtil.next(  ) )
        {
            Theme theme = new Theme(  );

            theme.setCodeTheme( daoUtil.getString( 1 ) );
            theme.setThemeDescription( daoUtil.getString( 2 ) );
            theme.setPathImages( daoUtil.getString( 3 ) );
            theme.setPathCss( daoUtil.getString( 4 ) );
            theme.setThemeAuthor( daoUtil.getString( 5 ) );
            theme.setThemeAuthorUrl( daoUtil.getString( 6 ) );
            theme.setThemeVersion( daoUtil.getString( 7 ) );
            theme.setThemeLicence( daoUtil.getString( 8 ) );
            theme.setPathJs( daoUtil.getString( 9 ) );

            themeList.add( theme );
        }

        daoUtil.free(  );

        return themeList;
    }

    /**
     * {@inheritDoc}
     */
    public ReferenceList getThemesList( Plugin plugin )
    {
        ReferenceList themesList = new ReferenceList(  );
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECT_THEME, plugin );

        daoUtil.executeQuery(  );

        while ( daoUtil.next(  ) )
        {
            themesList.addItem( daoUtil.getString( 1 ), daoUtil.getString( 2 ) );
        }

        daoUtil.free(  );

        return themesList;
    }

    /**
     * {@inheritDoc }
     */
    public void setGlobalTheme( String strGlobalTheme, String strGlobalThemeVersion, Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_UPDATE_GLOBAL_THEME, plugin );

        daoUtil.setString( 1, strGlobalTheme );

        daoUtil.executeUpdate(  );

        daoUtil.free(  );

        DAOUtil daoUtil2 = new DAOUtil( SQL_QUERY_UPDATE_GLOBAL_THEME_VERSION, plugin );

        daoUtil2.setString( 1, strGlobalThemeVersion );

        daoUtil2.executeUpdate(  );

        daoUtil2.free(  );
    }
   
    /**
     * {@inheritDoc }
     */
    public Theme getGlobalTheme( Plugin plugin )
    {
        Theme theme = null;
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECT_GLOBAL_THEME, plugin );

        daoUtil.executeQuery(  );

        if ( daoUtil.next(  ) )
        {
            theme = new Theme(  );
            theme.setCodeTheme( daoUtil.getString( 1 ) );
            theme.setThemeDescription( daoUtil.getString( 2 ) );
            theme.setPathImages( daoUtil.getString( 3 ) );
            theme.setPathCss( daoUtil.getString( 4 ) );
            theme.setThemeAuthor( daoUtil.getString( 5 ) );
            theme.setThemeAuthorUrl( daoUtil.getString( 6 ) );
            theme.setThemeVersion( daoUtil.getString( 7 ) );
            theme.setThemeLicence( daoUtil.getString( 8 ) );
            theme.setPathJs( daoUtil.getString( 9 ) );
        }

        daoUtil.free(  );

        return theme;
    }
}
