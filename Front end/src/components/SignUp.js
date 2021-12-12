import React, { Component } from "react";

import AuthService from "../services/AuthService";






export default class SignUp extends Component {
  constructor(props) {
    super(props);
    this.handleRegister = this.handleRegister.bind(this);
    this.onChangeUsername = this.onChangeUsername.bind(this);
    this.onChangeEmail = this.onChangeEmail.bind(this);
    this.onChangePassword = this.onChangePassword.bind(this);

    this.state = {
      username: "",
      email: "",
      password: "",
      successful: false,
      message: ""
    };
  }

  onChangeUsername(e) {
    this.setState({
      username: e.target.value
    });
  }

  onChangeEmail(e) {
    this.setState({
      email: e.target.value
    });
  }

  onChangePassword(e) {
    this.setState({
      password: e.target.value
    });
  }

  handleRegister(e) {
    e.preventDefault();

    this.setState({
      message: "",
      successful: false
    });


      AuthService.register(
        this.state.username,
        this.state.email,
        this.state.password
      );
      this.setState({
        successful: true
      });
    
  }

  render() {
    return (
        <div className ="login-form">
        <form onSubmit={this.handleRegister}>

        <label for="username">Username</label>
        <input type="text" id="username" name="username" placeholder="Enter Username.."
        value ={this.state.username} onChange={this.onChangeUsername} />
        <label for="password">Password</label>
        <input type="text" id="password" name="password" placeholder="Enter Password.."
        value ={this.state.password} onChange={this.onChangePassword}/>
        <label for="email">Email</label>
        <input type="text" id="email" name="email" placeholder="Enter Email.."
        value ={this.state.email} onChange={this.onChangeEmail}/>
        
            <input  type="submit" value="Submit"/>
            <input type="reset" value="Reset"/>
        </form>
    </div>
      
    );
  }
}
