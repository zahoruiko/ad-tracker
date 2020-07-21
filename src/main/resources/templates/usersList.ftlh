<#import "parts/baseTemplate.ftlh" as template>
<#import "parts/userAdmin.ftlh" as userAdmin>
<#import "parts/paginator.ftlh" as paginator>

<@template.baseTemplate>

    <@userAdmin.usersList />
    <@paginator.paginator url page />    

</@template.baseTemplate>
