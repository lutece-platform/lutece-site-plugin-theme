/*
 * Copyright (c) 2002-2013, Mairie de Paris
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

import fr.paris.lutece.plugins.theme.service.ThemePlugin;
import fr.paris.lutece.portal.business.style.Theme;
import fr.paris.lutece.portal.service.plugin.Plugin;
import fr.paris.lutece.portal.service.spring.SpringContextService;
import fr.paris.lutece.util.ReferenceList;

import java.util.Collection;


/**
 * This class provides instances management methods (create, find, ...) for Theme objects
 */
public final class ThemeHome
{
    // Static variable pointed at the DAO instance
    private static IThemeDAO _dao = (IThemeDAO) SpringContextService.getPluginBean( ThemePlugin.PLUGIN_NAME,
            "theme.themeDAO" );

    /**
     * Creates a new ThemeHome object.
     */
    private ThemeHome(  )
    {
    }

    /**
     * Creation of an instance of a theme
     *
     * @param theme An instance of a theme which contains the informations to store
     * @param plugin Plugin
     * @return The instance of a theme which has been created with its primary key.
     */
    public static Theme create( Theme theme, Plugin plugin )
    {
        _dao.insert( theme, plugin );

        return theme;
    }

    /**
     * Update of the theme which is specified
     *
     * @param theme The instance of the theme which contains the data to store
     * @param plugin Plugin
     * @return The instance of the theme which has been updated
     */
    public static Theme update( Theme theme, Plugin plugin )
    {
        _dao.store( theme, plugin );

        return theme;
    }

    /**
     * Remove the theme whose identifier is specified in parameter
     *
     * @param strCodeTheme The identifier of the theme to remove
     * @param plugin Plugin
     */
    public static void remove( String strCodeTheme, Plugin plugin )
    {
        _dao.delete( strCodeTheme, plugin );
    }

    /**
     * Returns an instance of an theme whose identifier is specified in parameter
     *
     * @param strCodeTheme The theme primary key
     * @param plugin Plugin
     * @return an instance of a theme
     */
    public static Theme findByPrimaryKey( String strCodeTheme, Plugin plugin )
    {
        return _dao.load( strCodeTheme, plugin );
    }

    /**
     * Return the list of all the themes
     *
     * @param plugin Plugin
     * @return A collection of themes objects
     */
    public static Collection<Theme> getThemesList( Plugin plugin )
    {
        return _dao.selectThemesList( plugin );
    }

    /**
     * Returns a reference list which contains all the themes
     *
     * @param plugin Plugin
     * @return a reference list
     */
    public static ReferenceList getThemes( Plugin plugin )
    {
        return _dao.getThemesList( plugin );
    }

    /**
     * Checks if the theme is among existing themes
     *
     * @param strCodeTheme The theme to check
     * @param plugin Plugin
     * @return True if the theme is valid
     */
    public static boolean isValidTheme( String strCodeTheme, Plugin plugin )
    {
        Theme theme = ThemeHome.findByPrimaryKey( strCodeTheme, plugin );

        if ( theme != null )
        {
            return true;
        }

        return false;
    }

    /**
     * Sets the global theme
     *
     * @param strGlobalTheme The Global Theme
     * @param plugin Plugin
     */
    public static void setGlobalTheme( String strGlobalTheme, Plugin plugin )
    {
        _dao.setGlobalTheme( strGlobalTheme, plugin );
    }

    /**
     * Returns the global theme
     *
     * @param plugin Plugin
     * @return The Global Theme
     */
    public static Theme getGlobalTheme( Plugin plugin )
    {
        return _dao.getGlobalTheme( plugin );
    }
}
