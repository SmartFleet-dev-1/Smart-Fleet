export interface Insurance {
  insuranceId?: number;
  providerName?: string;
  policyNumber?: string;
  startDate?: string;
  endDate?: string;
  premiumAmount?: number;
  coverageType?: string;

  vehicleId?: number;
  vehicleNumber?: string;
}