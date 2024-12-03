package DAO.implementations;

/*public class DAORoomImpl extends DbConnection implements RoomDAO {

    @Override
    public void add(Room newRoom) {
        DbConnection connection = new DbConnection();
        String checkSql = "SELECT COUNT(*) FROM room WHERE name = ?";
        String sql = "INSERT INTO room (idroom, name, price, level, escaperoom_idescaperoom) VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement checkStmt = connection.getConnection().prepareStatement(checkSql)) {
            checkStmt.setString(1, newRoom.getName());
            ResultSet rs = checkStmt.executeQuery();
            rs.next();
            int result = rs.getInt(1);

            if (result > 0) {
                System.err.println("A room with the same name already exists in the database. Please, try creating a new one with a different name.");
                return;
            }
            try (PreparedStatement stmt = connection.getConnection().prepareStatement(sql)){
                stmt.setInt(1, newRoom.getIdRoom());
                stmt.setString(2, newRoom.getName());
                stmt.setDouble(3, newRoom.getPrice());
                stmt.setString(4, newRoom.getLevel().getLevelName());
                stmt.setString(5, "1");
                stmt.executeUpdate();
                System.out.println("Room successfully created.");
            }
        } catch (SQLException e) {
            System.err.println("Error inserting the room to the database: " + e.getMessage());
        }
    }

    @Override
    public List<Room> showData() {
        List<Room> rooms = null;
        DbConnection connection = new DbConnection();
        try (PreparedStatement stmt = connection.getConnection().prepareStatement("SELECT * FROM room WHERE enabled = 1")){
            rooms = new ArrayList<Room>();
            ResultSet rs = stmt.executeQuery();

            while(rs.next()) {
                Room room = new Room();
                room.setIdRoom(rs.getInt("idRoom"));
                room.setName(rs.getString("name"));
                room.setPrice(rs.getDouble("price"));
                room.setLevel(Level.valueOf(rs.getString("level").toUpperCase()));
                rooms.add(room);
            }

        } catch (SQLException e) {
            System.out.println("Error extracting the data: " + e.getMessage());
        }
        return rooms;
    }

    @Override
    public Room findRoom() throws NoRoomsException {
        DbConnection connection = new DbConnection();
        ResultSet rs = null;
        Room room = null;
        String room_name = "";
        String sql = "SELECT * FROM room WHERE name = ?";

        room_name = Entry.readString("Introduce the room's name:");

        try(PreparedStatement stmt = connection.getConnection().prepareStatement(sql)) {
            stmt.setString(1, room_name);
            rs = stmt.executeQuery();

            if(rs.next()){
                room = new Room();
                room.setIdRoom(rs.getInt("id"));
                room.setName(rs.getString("name"));
                room.setPrice(rs.getDouble("price"));
                room.setLevel(Level.valueOf(rs.getString("level").toUpperCase()));
            }else if(!rs.next()){
                throw new NoRoomsException("Error. Room not found. Please, try again.");
            }
        } catch (SQLException e) {
            System.err.println("Error while trying to connect to the database." + e.getMessage());
        }
        return room;
    }

    @Override
    public void removeRoom(Room room) {

    }


}*/
