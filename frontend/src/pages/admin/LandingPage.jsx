import React from "react";
import Navbar from "../../components/Navbar";
import styled from "@emotion/styled"
import { FaCheckSquare, FaToolbox, FaInbox, FaChartPie } from "react-icons/fa";
import Link from 'next/link';

const Page = styled.div`
  position: absolute;
  height: 1080px;
  width: 390px;
  background-color: #1E1E1E;
  margin:0;
`;
const DashboardContainer = styled.div`
  display: flex;
  flex-direction: column;
  height: 1000px;
  `;

const MyTasksCard = styled.div`
position:absolute;
background-color:#424242;
margin-left:3vh;
margin-top: 10vh;
width: 160px;
height: 150px;
border-radius: 10px;
box-shadow: 0px 4px 8px rgba(0, 0, 0, 0.5);
`;
const ManageBookingsCard = styled.div`
position:absolute;
background-color:#424242;
margin-left:24vh;
margin-top: 10vh;
width: 160px;
height: 150px;
border-radius: 10px;
box-shadow: 0px 4px 8px rgba(0, 0, 0, 0.5);
`;
const ManageRentalsCard = styled.div`
position:absolute;
background-color:#424242;
margin-left:24vh;
margin-top: 30vh;
width: 160px;
height: 150px;
border-radius: 10px;
box-shadow: 0px 4px 8px rgba(0, 0, 0, 0.5);
`;
const TrackBookingsCard = styled.div`
position:absolute;
background-color:#424242;
margin-left:3vh;
margin-top: 30vh;
width: 160px;
height: 150px;
border-radius: 10px;
box-shadow: 0px 4px 8px rgba(0, 0, 0, 0.5);
`;
const UnkownCard = styled.div`
position:absolute;
background-color:#424242;
margin-left:3vh;
margin-top: 50vh;
width: 340px;
height: 150px;
border-radius: 10px;
box-shadow: 0px 4px 8px rgba(0, 0, 0, 0.5);
`;
const MyTasksTitle = styled.p`
position:absolute;
margin-left: 2vh;
margin-top: 2vh;
font-weight: 500;
color: #EFEFEF;
font-size: 18px;
`;
const ManageBookingsTitle = styled.p`
position:absolute;
margin-top: 2vh;
line-height:20px;
margin-left: 2vh;
font-weight: 500;
color: #EFEFEF;
font-size: 18px;
`;
const ManageRentalsTitle = styled.p`
position:absolute;
margin-top: 2vh;
line-height:20px;
margin-left: 2vh;
font-weight: 500;
color: #EFEFEF;
font-size: 18px;
`;
const TrackBookingsTitle = styled.p`
position:absolute;
margin-top: 2vh;
line-height:20px;
margin-left: 2vh;
font-weight: 500;
color: #EFEFEF;
font-size: 18px;
`;

const MyTasksIcon = styled(FaCheckSquare)`
position: absolute;
width: 50px;
height: 50px;
margin-left: 2vh;
margin-top: 7vh;
color: yellow;
`;
const BookingRequestIcon = styled(FaInbox)`
position: absolute;
width: 50px;
height: 50px;
margin-left: 2vh;
margin-top: 7vh;
color: yellow;
`;

const TrackBookingsIcon = styled(FaChartPie)`
position: absolute;
width: 50px;
height: 50px;
margin-left: 2vh;
margin-top: 7vh;
color: yellow;
`;
const ManageRentalsIcon = styled(FaToolbox)`
position: absolute;
width: 50px;
height: 50px;
margin-left: 2vh;
margin-top: 7vh;
color: yellow;
`;



const LandingPage = () => {
  return (
    <Page>
      <Navbar />
      <DashboardContainer>
        <MyTasksCard>
          <MyTasksTitle>
            My Tasks
          </MyTasksTitle>
          <MyTasksIcon />
        </MyTasksCard>

        <Link href={{
          pathname: "/admin/BookingRequests"
        }}>
          <ManageBookingsCard>
            <ManageBookingsTitle>
              Booking Requests
            </ManageBookingsTitle>
            <BookingRequestIcon />
          </ManageBookingsCard>
        </Link>
        <Link href={{ pathname: "/admin/ManageRentals" }}>
          <ManageRentalsCard>
            <ManageRentalsTitle>
              Manage Rentals
            </ManageRentalsTitle>
            <ManageRentalsIcon />
          </ManageRentalsCard>
        </Link>
        <Link href={{ pathname: "/admin/TrackBookings" }}>
          <TrackBookingsCard>
            <TrackBookingsTitle>
              Track Bookings
            </TrackBookingsTitle>
            <TrackBookingsIcon />
          </TrackBookingsCard>
        </Link>
        <UnkownCard>
        </UnkownCard>
      </DashboardContainer>

    </Page>
  )
}
export default LandingPage;