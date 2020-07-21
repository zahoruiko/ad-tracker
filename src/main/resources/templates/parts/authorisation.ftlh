<#macro loginForm>
    <div><h4>Sign in:</h4></div>
    <div>${message?ifExists}</div>
    <form action="/login" method="POST">
        <div class="form-group">
            <label>User Name : </label>
            <input type="text" class="form-control ${(usernameError??)?string('is-invalid', '')}" name="username" value="${name?ifExists}" placeholder="Name" />
            <#if usernameError??>
                <div class="invalid-feedback">
                    ${usernameError}
                </div>
            </#if>
        </div>
        <div class="form-group">
            <label>Password: </label>
            <input type="password" class="form-control ${(passwordError??)?string('is-invalid', '')}" name="password" value="" placeholder="Password" />
            <#if passwordError??>
                <div class="invalid-feedback">
                    ${passwordError}
                </div>
            </#if>            
        </div>
        <input type="hidden" name="_csrf" value="${_csrf.token}" />
        <button type="submit" class="btn btn-primary">Sign in!</button>
    </form>
    <div class="mt-2">or <a href="/registration">Sign up!</a></div>
</#macro>


<#macro logoutButton>
    <form action="/logout" method="post">
        <input type="hidden" name="_csrf" value="${_csrf.token}" />
        <button type="submit" class="btn btn-primary">Sign out</button>
    </form>
</#macro>


<#macro registrationForm>
    <div><h4>Sign up:</h4></div>
    <div><font color='red'>${message?ifExists}</font></div>
    <form method="POST">
        <div class="form-group">
            <label for="name">Name:</label><br>
            <input type="text" class="form-control ${(nameError??)?string('is-invalid', '')}" name="name" value="<#if user??>${user.name}</#if>" placeholder="User name" />
            <#if nameError??>
                <div class="invalid-feedback">
                    ${nameError}
                </div>
            </#if>
        </div>
        <div class="form-group">
            <label for="email">E-mail:</label><br>
            <input type="email" class="form-control ${(emailError??)?string('is-invalid', '')}" name="email"  value="<#if user??>${user.email}</#if>" placeholder="User E-mail" />
            <small id="emailHelp" class="form-text text-muted">We'll never share your email with anyone else.</small>
            <#if emailError??>
                <div class="invalid-feedback">
                    ${emailError}
                </div>
            </#if>
        </div>
        <div class="form-group">
            <label for="password">Password:</label><br>
            <input type="password" class="form-control ${(passwordError??)?string('is-invalid', '')}" name="password"  value="" placeholder="Password" /><br/>
            <#if passwordError??>
                <div class="invalid-feedback">
                    ${passwordError}
                </div>
            </#if>
            <input type="password" class="form-control ${(password2Error??)?string('is-invalid', '')}" name="password2"  value="" placeholder="Password one more time" /><br/>
            <small id="passwordHelp" class="form-text text-muted">The reliable password should be at least 8 characters long and contain uppercase and lowercase letters, numbers, and special characters.</small>
            <#if password2Error??>
                <div class="invalid-feedback">
                    ${password2Error}
                </div>
            </#if>
        </div>
        <input type="checkbox" id="rulesAcceptance" name="rulesAcceptance" />
        <label for="rulesAcceptance">I accept rules and terms of this site</label><br>
            <#if rulesacceptanceError??>
                <div class="invalid-feedback">
                    ${rulesacceptanceError}
                </div>
            </#if>

        <input type="hidden" name="_csrf" value="${_csrf.token}" />
        <button type="submit" class="btn btn-primary">Submit</button>
    </form>
</#macro>