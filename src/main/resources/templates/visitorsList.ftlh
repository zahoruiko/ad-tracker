<#import "parts/baseTemplate.ftlh" as template>
<#import "parts/visitors.ftlh" as visitors>
<#import "parts/paginator.ftlh" as paginator>

<@template.baseTemplate>

    <@visitors.visitorsList />
    <@paginator.paginator url page />

</@template.baseTemplate>
