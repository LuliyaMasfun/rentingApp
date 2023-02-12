"use client";
import React from "react";
import axios from "axios";
import { useRouter } from "next/router";
import { useEffect, useState } from "react";
import { FaRegClock, FaMapMarkerAlt } from "react-icons/fa";
import styled from "@emotion/styled";
import Image from "next/image";
import sound2 from "../../public/rode2.png";
import camera2 from "../../public/canon2.png";
import light2 from "../../public/aputure2.png";
import Calendar from "../components/Calendar";
import "../styles/globals.css";
import Link from "next/link";

const Page = styled.div`
  position: absolute;
  height: 1384px;
  width: 390px;
  background-color: #1e1e1e;
  margin: 0;
`;

const EquipmentImage = styled(Image)``;
const Brand = styled.h2`
  position: absolute;
  margin-left: 3vh;
  margin-top: 2vh;
  color: #a86a61;
  font-size: 16px;
`;
const Name = styled.h1`
  position: absolute;
  margin-left: 3vh;
  margin-top: 1vh;
  color: #ffffff;
  font-weight: 500;
  font-size: 22px;
`;

const LocationBg = styled.div`
  position: absolute;
  margin-left: 3vh;
  margin-top: 2.5vh;
  background-color: #323232;
  width: 160px;
  height: 55px;
  border-radius: 10px;
`;
const Location = styled.p`
  position: absolute;
  margin-top: 3vh;
  margin-left: 2.5vh;
  font-size: 16px;
  color: white;
`;
const LocationIcon = styled(FaMapMarkerAlt)`
  position: absolute;
  margin-top: 0.5vh;
  margin-left: 2vh;
  color: #8e8e8e;
`;
const LocationTxt = styled.p`
  position: absolute;
  margin-top: 0.5vh;
  margin-left: 4.5vh;
  font-size: 12px;
  color: #8e8e8e;
`;

const MaxDaysToRentBg = styled.div`
  position: absolute;
  margin-left: 24vh;
  margin-top: 2.5vh;
  background-color: #323232;
  width: 160px;
  height: 55px;
  border-radius: 10px;
`;
const MaxDaysToRent = styled.p`
  position: absolute;
  margin-top: 3vh;
  margin-left: 2vh;
  font-size: 16px;
  color: white;
`;
const MaxDaysToRentTxt = styled.p`
  position: absolute;
  margin-top: 0.5vh;
  margin-left: 4.5vh;
  font-size: 12px;
  color: #8e8e8e;
`;
const ClockIcon = styled(FaRegClock)`
  position: absolute;
  margin-top: 0.5vh;
  margin-left: 2vh;
  color: #8e8e8e;
`;

const DescBg = styled.div`
  position: absolute;
  margin-top: 12vh;
  margin-left: 3vh;
  background-color: #f3f3f3;
  width: 340px;
  height: 185px;
  border-radius: 10px;
`;
const DescTxt = styled.p`
  position: absolute;
  margin-top: 1vh;
  margin-left: 2.4vh;
  font-size: 16px;
  font-weight: 500;
`;
const Description = styled.h2`
  position: absolute;
  margin-left: 0vh;
  margin-top: 2vh;
  font-size: 12px;
  color: #aaaaaa;
  padding: 20px;
`;
const ViewMore = styled.span`
  text-decoration: underline;
  color: #f18f85;
`;

const MakeReservationBtn = styled.button`
  position: absolute;
  background-color: #e8e337;
  width: 267px;
  height: 38px;
  margin-top: 15vh;
  margin-left: 7vh;
  border-radius: 5px;
  font-size: 14px;
  font-weight: 500;
`;

const EquipmentItem = () => {
  const [data, setData] = useState([]);
  const router = useRouter();
  const { id } = router.query;

  useEffect(() => {
    const fetchData = async () => {
      try {
        const response = await axios.get(
          `http://localhost:8080/getEquipment/${id}`
        );
        setData(response.data);
        console.log(response.data);
      } catch (error) {
        console.error(error);
      }
    };
    fetchData();
  }, [id]);

  const checkType = (imageType) => {
    if (imageType.equipmentType == "CAMERA") {
      return camera2;
    } else if (imageType.equipmentType == "LIGHT") {
      return light2;
    } else {
      return sound2;
    }
  };
  // equipmentBrand input to upperCase
  const equipmentBrand = data.equipmentBrand
    ? data.equipmentBrand.toUpperCase()
    : "";

  return (
    <div>
      {data ? (
        <Page>
          <EquipmentImage src={checkType(data)} />
          <Brand>{equipmentBrand}</Brand>
          <Name>{data.equipmentName}</Name>
          <div>
            <LocationBg>
              <LocationIcon />
              <LocationTxt> Location</LocationTxt>
              <Location>{data.equipmentLocation}</Location>
            </LocationBg>
            <MaxDaysToRentBg>
              <ClockIcon />
              <MaxDaysToRentTxt>Max days to rent</MaxDaysToRentTxt>
              <MaxDaysToRent>{data.maxDaysToRent} Days</MaxDaysToRent>
            </MaxDaysToRentBg>
            <DescBg>
              <DescTxt>Description</DescTxt>
              <Description>
                {data.equipmentDescription} ++++++Lorem ipsum dolor sit amet,
                consectetur adipiscing elit, sed do eiusmod tempor incididunt ut
                labore et dolore magna aliqua. Ut enim ad minim veniam{" "}
                <ViewMore>View more</ViewMore>
              </Description>
            </DescBg>
          </div>
          <Calendar />
          <Link href="/MyBookings">
            <MakeReservationBtn>Make Reservation</MakeReservationBtn>
          </Link>
        </Page>
      ) : (
        <p>Loading...</p>
      )}
    </div>
  );
};

export default EquipmentItem;
