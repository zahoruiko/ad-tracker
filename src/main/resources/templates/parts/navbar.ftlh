<#import "authorisation.ftlh" as authorisation>
<#include "security.ftlh">

<nav class="navbar navbar-expand-lg navbar-light bg-light">
    <a class="navbar-brand" href="/">AdsTracker</a>
    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
    </button>
    <div class="collapse navbar-collapse" id="navbarSupportedContent">
        <#if !isAdmin && !isUser>
            <ul class="navbar-nav">
                <li class="nav-item active">
                    <a class="nav-link" href="/info/About">About</a>
                </li>
                <li class="nav-item active">
                    <a class="nav-link" href="/info/Contacts">Contacts</a>
                </li>
                <li class="nav-item active">
                    <a class="nav-link" href="/info/Terms">Terms</a>
                </li>
                <li class="nav-item active">
                    <a class="nav-link" href="/info/F.A.Q.">F.A.Q.</a>
                </li>
                <li class="nav-item active">
                    <a class="nav-link" href="/login">Sign in</a>
                </li>
                <li class="nav-item active">
                    <a class="nav-link" href="/registration">Sign up</a>
                </li>
                <li class="nav-item active">
                    <div class="navbar-text">Hello, ${name}!</div>
                </li>
            </ul>
        <#else>
            <ul class="navbar-nav">
                <li class="nav-item active">
                    <a class="nav-link" href="/addNewCampaign">Add new campaign</a>    
                </li>
                <li class="nav-item active">
                    <a class="nav-link" href="/campaignsList">My campaigns</a>    
                </li>
                <li class="nav-item active">
                    <a class="nav-link" href="/userDetails">User details</a>      
                </li>
                <#if isAdmin>
                    <li class="nav-item active">
                        <a class="nav-link" href="/admin">Users list</a>
                    </li>
                    <li class="nav-item active">
                        <a class="nav-link" href="/admin/articlesList">Articles list</a>
                    </li>
                </#if>
                <li class="nav-item active">
                    <div class="navbar-text">Hello, ${name}!</div>
                </li>
                <li class="nav-item active ml-2">
                    <div><@authorisation.logoutButton /></div>
                </li>
            </ul>
        </#if>
    </div>
</nav>