<#import "parts/baseTemplate.ftlh" as template>
<#import "parts/authorisation.ftlh" as authorisation>
<#import "parts/menu.ftlh" as menu>

<@template.baseTemplate>

    <#include "parts/security.ftlh">
    <#if !isAdmin && !isUser>
        <@authorisation.loginForm />
    <#else>
        <@menu.userMainMenu />
    </#if>

</@template.baseTemplate>