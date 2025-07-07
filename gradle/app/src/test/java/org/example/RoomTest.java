package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.example.Databases.DatabaseRoom;
import org.example.Logic.Room;


// @ExtendWith(MockitoExtension.class)
class RoomTest{


    // @Mock
    private DatabaseRoom mockDatabaseRoom;

    //@InjectMocks
    private Room room;

    @BeforeEach
    void setUp() {
        mockDatabaseRoom = mock(DatabaseRoom.class);
        room = new Room(mockDatabaseRoom);
    }

    @Test
    void testAddRoom_Success() {
        // Rummet med nedstående parametrar ska visa True
        when(mockDatabaseRoom.addRoom(101, 1, 2, 30, 500.0, "Deluxe")).thenReturn(true);

        boolean result = room.addRoom(101, 1, 2, 30, 500.0, "Deluxe");

        assertTrue(result);
        // Verifiera att mockDatabaseRoom.addRoom anropades exakt en gång
        verify(mockDatabaseRoom, times(1)).addRoom(101, 1, 2, 30, 500.0, "Deluxe");
    }

    @Test
    void testAddRoom_Failure() {
        // Rummet med nedstående parametrar ska visa False
        when(mockDatabaseRoom.addRoom(102, 1, 2, 25, 400.0, "Standard")).thenReturn(false);

        boolean result = room.addRoom(102, 1, 2, 25, 400.0, "Standard");

        assertFalse(result);
        verify(mockDatabaseRoom, times(1)).addRoom(102, 1, 2, 25, 400.0, "Standard");
    }

    @Test
    void testDeleteRoom_Success() {
        // Rum 101 med locID 1 får tas bort
        when(mockDatabaseRoom.deleteRoom(101, 1)).thenReturn(1);

        boolean result = room.deleteRoom(101, 1);

        assertTrue(result);
        
        verify(mockDatabaseRoom, times(1)).deleteRoom(101, 1);
    }

    @Test
    void testDeleteRoom_Failure() {
        // Nedstående rum får inte tas bort
        when(mockDatabaseRoom.deleteRoom(102, 1)).thenReturn(0);

        boolean result = room.deleteRoom(102, 1);

        assertFalse(result);
        verify(mockDatabaseRoom, times(1)).deleteRoom(102, 1);
    }
    /* 
    @Test
    void testNewRoomType_Success() {
        // Rummet är Deluxe och vi ska ändra till Familj, samma room_id är angivet i båda fallen
        when(mockDatabaseRoom.getRoomType(101, 1)).thenReturn("Deluxe");

        boolean result = room.isNewRoomType("Familj", 101, 1);

        assertTrue(result);
        // Verifiera att mockDatabaseRoom.getRoomType() anropades exakt en gång
        verify(mockDatabaseRoom, times(1)).getRoomType(101, 1);
    }

    @Test
    void testNewRoomType_Fail() {
        // Rummet är Deluxe och vi ska ändra till Deluxe, samma room_id är angivet i båda fallen
        when(mockDatabaseRoom.getRoomType(101, 1)).thenReturn("Deluxe");

        boolean result = room.isNewRoomType("Deluxe", 101, 1);

        assertFalse(result);
        // Verifiera att mockDatabaseRoom.getRoomType() anropades exakt en gång
        verify(mockDatabaseRoom, times(1)).getRoomType(101, 1);
    }

    @Test
    void changedRoomDetails_Success() {
      // Vi försöker ändra "Deluxe" till rumstypen "Standard"
      when(mockDatabaseRoom.getRoomType(101, 1)).thenReturn("Deluxe");

      // Vi behöver en mockad Room-instans för att hantera de metoder som anropas internt
      Room spyRoom = spy(room);

      // Mocka newRoomType så att vi kan välja returvärde
      doReturn(true).when(spyRoom).isNewRoomType("Standard", 101, 1);

      // Mocka changeToStandard så att den inte gör något under testet
      doNothing().when(spyRoom).changeToStandard(101, 1);

      boolean result = spyRoom.changedRoomDetails("Standard", 101, 1);

      assertTrue(result);
      // Verifiera att spyRoom.changeToStandard() "anropats" en gång
      verify(spyRoom, times(1)).changeToStandard(101, 1);
    }

    @Test
    void testChangedRoomDetails_Fail() {
        // Rummet är redan "Deluxe", vi försöker ändra till samma typ
        // Detta bör inte kunde inträffa eftersom isNewRoomType är blockar
        when(mockDatabaseRoom.getRoomType(101, 1)).thenReturn("Deluxe");

        Room spyRoom = spy(room);

        doReturn(false).when(spyRoom).isNewRoomType("Deluxe", 101, 1);

        boolean result = spyRoom.changedRoomDetails("Deluxe", 101, 1);

        assertFalse(result);
        // Verifierar att vi inte anropat några av nedstående metoder (metoder som uppdaterar rumsdetaljer)
        verify(spyRoom, never()).changeToStandard(anyInt(), anyInt());
        verify(spyRoom, never()).changeToDeluxe(anyInt(), anyInt());
        verify(spyRoom, never()).changeToFamilj(anyInt(), anyInt());
    }
    */

}