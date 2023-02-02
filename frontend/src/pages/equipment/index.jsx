import Navbar from '@/components/Navbar'
import Axios from 'axios'
import { useEffect, useState } from 'react'

 const API_URL = 'http://localhost:8080/users'

// Hur man kan hämta data från en API genom ASYNC & AWAIT och sedan använda den i en funktion
// const getData = async () => {
//     const response = await Axios.get(API_URL)
//     const info = response.data.map((data) => allEquipments((data.name)))
//     return info
// }
const EquipmentDefault = () => {

    const [data, setData] = useState([])
    const [filterValue, setFilterValue] = useState("all")
    const [isLoading, setIsLoading] = useState(true)

    console.log(Axios.get(API_URL).then(res => {
        console.log(res.data)
    }).then(err => console.log(err)))

    useEffect(() => {
        const fetchData = async () => {
            try {
                const response = await Axios.get(API_URL, {
                    headers: "Access-Control-Allow-Origin: *"
                })
                console.log(response.data)
                const info = response.data
                setData(info)
                setIsLoading(false)
            } catch (error) {
                if (error.response) {
                    console.log(error.response.data)
                    console.log(error.response.status)
                    console.log(error.response.headers)
                }
                else {
                    console.log(`Error: ${error.message}`)
                }

            }
        }
        fetchData()

    }, [])

    if (isLoading) {
        return <div>Loading...</div>
    }

    const checkType = (obj) => {

        if (obj.equipmentType == "CAMERA") {
            return camera;
        } else if (obj.equipmentType == "LIGHT") {
            return light;
        } else {
            return sound;
        }
    }

    const onFilterValueSelected = (filterValue) => {
        setFilterValue(filterValue)

    }



    return (
        <main className='min-h-screen flex-grow'>
            <Navbar />
            <div className='flex border-8 m-8'>
                {data.map(item => (
                    <div key={item.id}>
                        <h2>{item.name}</h2>
                        <h2> {item.last_name}</h2>
                        <p>{item.email}</p>
                    </div>

                ))}
            </div>



        </main>

    )
}
export default EquipmentDefault 