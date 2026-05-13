export interface MaintenanceRecord {
  maintenanceId?: number;
  serviceDate?: string;
  serviceType?: string;
  serviceCenter?: string;
  serviceCost?: number;
  nextServiceDate?: string;
  remarks?: string;

  vehicleId?: number;
  vehicleNumber?: string;
}