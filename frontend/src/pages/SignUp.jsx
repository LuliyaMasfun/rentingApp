
import React from "react";
import styled from "@emotion/styled";
import { useState, useRef } from "react";
import Link from "next/link";
import "../styles/globals.css";
import { isEmail } from "validator";
import AuthService from "../services/auth.service";
import Form from "react-validation/build/form";
import Input from "react-validation/build/input";
import CheckButton from "react-validation/build/button";


const Page = styled.div`
  height: 900px;
  background-image: url("/bg.png");
  background-size: cover;
  background-position: center;
  background-color: #1e1e1e;
`;
const Title = styled.h1`
  position: absolute;
  margin-top: 14vh;
  margin-left: 6vh;
  font-size: 28px;
  font-weight: 500;
  margin-bottom: 20px;
  color: white;
`;

const Subtitle = styled.p`
  position: absolute;
  margin-top: 18vh;
  margin-left: 6vh;
  font-size: 18px;
  margin-bottom: 40px;
  color: white;
`;
const SignUpForm = styled(Form)`
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  height: 80vh;
  margin-left: -35vh;

  
`;
const InputFirstname = styled(Input)`
  position: absolute;
  margin-top: -70px;
  border: none;
  padding: 10px;
  width: 260px;
  background: transparent;
  border-bottom: 0.5px solid white;
  color: white;
  ::placeholder {
    color: #fff;
    opacity: 0.7;
  }
`;
const InputLastname = styled(Input)`
  position: absolute;
  margin-top: px;
  border: none;
  padding: 10px;
  width: 260px;
  background: transparent;
  border-bottom: 0.5px solid white;
  color: white;
  ::placeholder {
    color: #fff;
    opacity: 0.7;
  }
`;
const InputEmail = styled(Input)`
  position: absolute;
  margin-top: 70px;
  border: none;
  padding: 10px;
  width: 260px;
  background: transparent;
  border-bottom: 0.5px solid white;
  color: white;
  ::placeholder {
    color: #fff;
    opacity: 0.7;
  }
`;
const InputAddress = styled(Input)`
  position: absolute;
  margin-top: 140px;
  border: none;
  padding: 10px;
  width: 260px;
  background: transparent;
  border-bottom: 0.5px solid white;
  color: white;
  ::placeholder {
    color: #fff;
    opacity: 0.7;
  }
`;
const InputPhonenumber = styled(Input)`
  position: absolute;
  margin-top: 210px;
  border: none;
  padding: 10px;
  width: 260px;
  background: transparent;
  border-bottom: 0.5px solid white;
  color: white;
  ::placeholder {
    color: #fff;
    opacity: 0.7;
  }
`;
const InputBirthdate = styled(Input)`
  position: absolute;
  margin-top: 280px;
  border: none;
  padding: 10px;
  width: 260px;
  background: transparent;
  border-bottom: 0.5px solid white;
  color: white;
  ::placeholder {
    color: #fff;
    opacity: 0.7;
  }
`;
const InputPassword = styled(Input)`
  position: absolute;
  margin-top: 350px;
  border: none;
  padding: 10px;
  width: 260px;
  background: transparent;
  border-bottom: 0.5px solid white;
  color: white;
  ::placeholder {
    color: #fff;
    opacity: 0.7;
  }
`;

const ButtonSignUp = styled.button`
  position: absolute;
  border: none;
  margin-top: 55vh;
  padding: 10px;
  width: 280px;
  background-color: white;
  color: #1e1e1e;
  font-weight: 700;
  font-size: 16px;
  border-radius: 5px;
  box-shadow: 0px 3px rgba(0, 0, 0, 0.3);
`;

const UnderlinedText = styled.span`
  text-decoration: underline;
`;

const Login = styled.span`
  position: absolute;
  margin-top: 58vh;
  font-weight: 300;
  font-size: 12px;
  color: white;
  margin-left: 5vh;
`;
const Alert = styled.p`
  position: absolute;
  margin-top: 50vh;
  margin-left: 6vh;
  font-size: 18px;
  margin-bottom: 40px;
  color:white;
`;

const SuccessMessage = styled.div`
position:absolute;
margin-top: 10vh;
margin-left: 5vh;
color: white;
`



const required = (value) => {
  if (!value) {
    return (
      <Alert role="alert">
        This field is required!
      </Alert>
    );
  }
};

const validEmail = (value) => {
  if (!isEmail(value)) {
    return (
      <Alert role="alert">
        This is not a valid email.
      </Alert>
    );
  }
};

const vpassword = (value) => {
  if (value.length < 6 || value.length > 40) {
    return (
      <Alert role="alert">
        The password must be between 6 and 40 characters.
      </Alert>
    );
  }
};

const SignUp = () => {
  const form = useRef();
  const checkBtn = useRef();

  const [firstName, setFirstName] = useState("");
  const [lastName, setLastName] = useState("");
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [address, setAddress] = useState("");
  const [phoneNumber, setPhoneNumber] = useState("");
  const [birthdate, setBirthdate] = useState("");
  const [message, setMessage] = useState("");
  const [successful, setSuccessful] = useState(false);


  const onChangeFirstName = e => {
    setFirstName(e.target.value)
  };
  const onChangeLastname = e => {
    setLastName(e.target.value)
  };
  const onChangeEmail = e => {
    setEmail(e.target.value)
  };

  const onChangeAddress = e => {
    setAddress(e.target.value)
  };
  const onChangePhoneNumber = e => {
    setPhoneNumber(e.target.value)
  };
  const onChangeBirthdate = e => {
    setBirthdate(e.target.value)
  };

  const onChangePassword = e => {
    setPassword(e.target.value)
  };
  const handleRegister = (e) => {
    e.preventDefault();

    setMessage("");
    setSuccessful(false);

    form.current.validateAll();

    if (checkBtn.current.context._errors.length === 0) {
      AuthService.register(firstName, lastName, email, address, phoneNumber, birthdate, password).then(
        (response) => {
          setMessage("User Registered Successfully");

          setSuccessful(true);
        },
        (error) => {
          const resMessage =
            (error.response &&
              error.response.data &&
              error.response.data.message) ||
            error.message ||
            error.toString();

          setMessage(resMessage);
          setSuccessful(false);
        }
      );
    }
  };

  return (
    <Page>
      <Title>Create an account,</Title>
      <Subtitle>Lets get started, enter your details</Subtitle>

      <SignUpForm
        onSubmit={handleRegister} ref={form}>
        <div>
          {!successful && (
            <>
              <InputFirstname
                type="text"
                name="firstname"
                placeholder="Firstname"
                value={firstName}
                onChange={onChangeFirstName}
                validations={[required]}
              />
              <InputLastname
                type="text"
                name="lastname"
                placeholder="Lastname"
                value={lastName}
                onChange={onChangeLastname}
                validations={[required]}
              />
              <InputEmail
                type="text"
                name="email"
                placeholder="Email"
                value={email}
                onChange={onChangeEmail}
                validations={[required, validEmail]}
              />
              <InputAddress
                type="text"
                name="address"
                placeholder="Address"
                value={address}
                onChange={onChangeAddress}
                validations={[required]}
              />

              <InputPhonenumber
                type="number"
                name="phonenumber"
                placeholder="Phonenumber"
                value={phoneNumber}
                onChange={onChangePhoneNumber}
                validations={[required]}
              />
              <InputBirthdate
                type="date"
                name="birthdate"
                placeholder="Birthdate"
                value={birthdate}
                onChange={onChangeBirthdate}
                validations={[required]}
              />
              <InputPassword
                type="password"
                name="password"
                placeholder="Password"
                value={password}
                onChange={onChangePassword}
                validations={[required, vpassword]}
              />

              <ButtonSignUp type="submit" onClick={handleRegister}>Sign Up</ButtonSignUp>

            </>
          )}
          <SuccessMessage>
            {message && (
              <div className="form-group">
                <div
                  className={
                    successful ? "alert alert-success" : "alert alert-danger"
                  }
                  role="alert"
                >
                  {message}
                </div>
              </div>
            )}
          </SuccessMessage>
          <CheckButton ref={checkBtn} />
        </div>
        <Link href={{
          pathname: "/Login"
        }}>
          <Login>
            Already have an account? <UnderlinedText>Log in</UnderlinedText>
          </Login>
        </Link>

      </SignUpForm>

    </Page>
  );
};
export default SignUp;

