<#import "parts/baseTemplate.ftlh" as template>
<#import "parts/authorisation.ftlh" as authorisation>

<@template.baseTemplate>

    <#include "parts/security.ftlh">
    <#if !isAdmin && !isUser>
        <@authorisation.loginForm />
    <#else>
        <@authorisation.logoutButton />
    </#if>
        
</@template.baseTemplate>
