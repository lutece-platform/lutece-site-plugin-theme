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
package fr.paris.lutece.plugins.theme.web;

import java.text.Normalizer.Form;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;

import fr.paris.lutece.api.user.User;
import fr.paris.lutece.plugins.theme.service.ThemeResourceIdService;
import fr.paris.lutece.plugins.theme.service.ThemeService;
import fr.paris.lutece.plugins.theme.utils.constants.ThemeConstants;
import fr.paris.lutece.portal.business.rbac.RBAC;
import fr.paris.lutece.portal.business.style.Theme;
import fr.paris.lutece.portal.business.user.AdminUser;
import fr.paris.lutece.portal.service.message.AdminMessage;
import fr.paris.lutece.portal.service.message.AdminMessageService;
import fr.paris.lutece.portal.service.portal.ThemesService;
import fr.paris.lutece.portal.service.rbac.RBACService;
import fr.paris.lutece.portal.service.template.AppTemplateService;
import fr.paris.lutece.portal.service.util.AppPathService;
import fr.paris.lutece.portal.web.admin.PluginAdminPageJspBean;
import fr.paris.lutece.util.html.HtmlTemplate;
import fr.paris.lutece.util.http.SecurityUtil;
import fr.paris.lutece.portal.service.admin.AccessDeniedException;


/**
 * ThemeJspBean
 */
public class ThemeJspBean extends PluginAdminPageJspBean
{
    // Right
    public static final String RIGHT_MANAGE_THEMES = "THEME_MANAGEMENT";
    protected static final String UNAUTHORIZED = "Unauthorized";

    // Templates files path
    private static final String TEMPLATE_MANAGE_THEMES = "admin/plugins/theme/manage_themes.html";

    // JSP
    private static final String JSP_MANAGE_THEMES = "ManageThemes.jsp";

    /**
     * Returns the list of Themes
     *
     * @param request The Http request
     * @return the html code for display the manage themes page
     */
    public String getManageThemes( HttpServletRequest request )
    {
        boolean bPermissionModifyGlobalTheme = RBACService.isAuthorized( Theme.RESOURCE_TYPE, RBAC.WILDCARD_RESOURCES_ID,
            ThemeResourceIdService.PERMISSION_MODIFY_GLOBAL_THEME, (User) getUser( ) ) ;

        Map<String, Object> model = new HashMap<String, Object>(  );

        Collection<Theme> listThemes = ThemeService.getInstance(  ).getThemesList(  );
        
        model.put( ThemeConstants.MARK_THEMES_LIST, listThemes );
        model.put( ThemeConstants.MARK_THEME_DEFAULT, ThemeService.getInstance(  ).getGlobalTheme(  ) );
        model.put( ThemeConstants.MARK_BASE_URL, AppPathService.getBaseUrl( request ) );
        model.put( ThemeConstants.MARK_PERMISSION_MODIFY_GLOBAL_THEME, bPermissionModifyGlobalTheme );
        
        setPageTitleProperty( ThemeConstants.PROPERTY_MANAGE_THEMES_PAGE_TITLE );

        HtmlTemplate template = AppTemplateService.getTemplate( TEMPLATE_MANAGE_THEMES, getLocale(  ), model );

        return getAdminPage( template.getHtml(  ) );
    }

    /**
     * Modify the global theme
     * @param request The Http request
     * @return the html code for display the manage themes page
     */
    public String doModifyGlobalTheme( HttpServletRequest request )
    {
        String strUrl = StringUtils.EMPTY;
        String strTheme = request.getParameter( ThemeConstants.PARAMETER_THEME );
        String strThemeVersion = request.getParameter( ThemeConstants.PARAMETER_THEME_VERSION );

        if ( StringUtils.isNotBlank( strTheme ) )
        {
            ThemeService.getInstance(  ).setGlobalTheme( strTheme, strThemeVersion );
            strUrl = getHomeUrl( request );
        }
        else
        {
            strUrl = AdminMessageService.getMessageUrl( request, ThemeConstants.MESSAGE_OBJECT_NOT_FOUND,
                    AdminMessage.TYPE_STOP );
        }

        return strUrl;
    }

    /**
     * Modify the User theme
     * @param request The Http request
     * @param response The Http response
     * @return the html code for display the manage themes page
     */
    public String doModifyUserTheme( HttpServletRequest request, HttpServletResponse response )
    {
        String strUrl = StringUtils.EMPTY;
        String strTheme = request.getParameter( ThemeConstants.PARAMETER_THEME );
        String strForwardUrl = request.getParameter( ThemeConstants.PARAMETER_URL );

        if ( SecurityUtil.containsCleanParameters( request ) )
        {
            if ( StringUtils.isNotBlank( strTheme ) && StringUtils.isNotBlank( strForwardUrl ) )
            {
                ThemesService.setUserTheme( request, response, strTheme );
                strUrl = strForwardUrl;
            }
            else
            {
                strUrl = AdminMessageService.getMessageUrl( request, ThemeConstants.MESSAGE_OBJECT_NOT_FOUND,
                        AdminMessage.TYPE_STOP );
            }
        }
        else
        {
            strUrl = AppPathService.getBaseUrl( request );
        }

        return strUrl;
    }

    /**
     *
     * @param request The HttpServletRequest
     * @return the theme object from request parameter
     */
    private Theme getThemeFromRequest( HttpServletRequest request )
    {
        Theme theme = new Theme(  );

        theme.setCodeTheme( request.getParameter( ThemeConstants.PARAMETER_CODE_THEME ) );
        theme.setThemeDescription( request.getParameter( ThemeConstants.PARAMETER_THEME_DESCRIPTION ) );
        theme.setPathImages( request.getParameter( ThemeConstants.PARAMETER_PATH_IMAGES ) );
        theme.setPathCss( request.getParameter( ThemeConstants.PARAMETER_PATH_CSS ) );
        theme.setPathJs( request.getParameter( ThemeConstants.PARAMETER_PATH_JS ) );
        theme.setThemeAuthor( request.getParameter( ThemeConstants.PARAMETER_THEME_AUTHOR ) );
        theme.setThemeAuthorUrl( request.getParameter( ThemeConstants.PARAMETER_THEME_AUTHOR_URL ) );
        theme.setThemeVersion( request.getParameter( ThemeConstants.PARAMETER_THEME_VERSION ) );
        theme.setThemeLicence( request.getParameter( ThemeConstants.PARAMETER_THEME_LICENCE ) );

        return theme;
    }
}