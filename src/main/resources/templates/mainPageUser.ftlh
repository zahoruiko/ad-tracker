<#import "parts/baseTemplate.ftlh" as template>
<#import "parts/menu.ftlh" as menu>
<#import "parts/authorisation.ftlh" as authorisation>

<@template.baseTemplate>

    <#if !isAdmin and !isUser>
        <@authorisation.loginForm />
    <#else>
        <@menu.userMainMenu />
        <@authorisation.logoutButton />
    </#if>

</@template.baseTemplate>