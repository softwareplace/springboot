package com.example.shared.model

import com.github.softwareplace.springboot.security.model.UserData

class InMemoryUser : UserData {
    override fun getUsername() = "useremail@gmail.com"

    override fun getPassword() = "\$2a\$05\$YFHKImsuJ4HhM7aIXC0W0OoK6tsW3yNawBMgIa70WOdC4tXQid/FC"

    override fun authToken() = "\$2a\$05\$qKy7hpUAUIlutO1Gb9yhiuO0ExNGeyGljG3bCFKy4ABBWfpCZJZZ2"
}
