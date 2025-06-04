<?php
header('Content-Type: application/json; charset=utf-8');
require_once 'db.php';

if (!isset($_GET["class_name"]) || empty(trim($_GET["class_name"]))) {
    echo json_encode(array("status" => "error", "message" => "Missing or empty class_name"));
    exit;
}

$class_name = trim($_GET["class_name"]);

$stmt = $conn->prepare("
    SELECT s.day, s.lecture_number, s.subject_name
    FROM schedule_details s
    JOIN schedules sch ON s.schedule_id = sch.schedule_id
    WHERE sch.class_name = ?
    ORDER BY FIELD(s.day, 'Sunday', 'Monday', 'Tuesday', 'Wednesday', 'Thursday'), s.lecture_number
");

if (!$stmt) {
    echo json_encode(array("status" => "error", "message" => "Database error: " . $conn->errorInfo()[2]));
    exit;
}

$stmt->execute([$class_name]);
$schedule = $stmt->fetchAll(PDO::FETCH_ASSOC);

echo json_encode(array("status" => "ok", "data" => $schedule));
?>
