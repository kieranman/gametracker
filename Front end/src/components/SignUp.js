import React, { Component } from "react";

import AuthService from "../services/AuthService";
import {Button} from 'react-bootstrap';





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

    if(this.state.username =="" || this.state.password =="" || this.state.email =="" ){
      this.setState({
        message: "Error a field was left empty",
        successful:false
      })
        
      
    }
    else{
      AuthService.register(
        this.state.username,
        this.state.email,
        this.state.password
      ).then(response =>{
        this.setState({
          message: response.data.message,
          successful:true
        });
      },        error => {
        const resMessage =
          (error.response &&
            error.response.data &&
            error.response.data.message) ||
          error.message ||
          error.toString();

        this.setState({
          successful: false,
          message: resMessage
        });
      }
      )
    }


     
     
     
      // this.setState({
      //   successful: true
      // });
    
  }

  render() {
    return (
        <div className ="login-form">
        <form onSubmit={this.handleRegister}>

        <label for="username">Username</label>
        <input type="text"className="login-input" name="username" placeholder="Enter Username.."
        value ={this.state.username} onChange={this.onChangeUsername} />
        <label for="password">Password</label>
        <input type="text" className="login-input" name="password" placeholder="Enter Password.."
        value ={this.state.password} onChange={this.onChangePassword}/>
        <label for="email">Email</label>
        <input type="text" className="login-input" name="email" placeholder="Enter Email.."
        value ={this.state.email} onChange={this.onChangeEmail}/>
        
            <Button className="mt-auto font-weight-bold"
                                variant ="secondary" type="submit" value="Submit">Submit</Button>

            {this.state.message && (
              <div className="form-group">
                <div
                  className={
                    this.state.successful
                      ? "alert alert-success"
                      : "alert alert-danger"
                  }
                  role="alert"
                >
                  {this.state.message}
                </div>
              </div>
            )}
        </form>
    </div>
      
    );
  }
}
