
import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LoginComponent } from './auth/login/login.component';
import { RegisterComponent } from './auth/register/register.component';
import { DashboardComponent } from './component/dashboard/dashboard.component';
import { VehicleComponent } from './component/vehicle/vehicle.component';
import { DriverComponent } from './component/driver/driver.component';
import { MaintenanceComponent } from './component/maintenance/maintenance.component';
import { InsuranceComponent } from './component/insurance/insurance.component';
import { AuthGuard } from './auth.guard';

const routes: Routes = [
  // Open routes
  { path: 'login', component: LoginComponent },
  { path: 'register', component: RegisterComponent },

  // Protected routes
  { path: 'dashboard', component: DashboardComponent, canActivate: [AuthGuard] },
  { path: 'vehicles', component: VehicleComponent, canActivate: [AuthGuard] },
  { path: 'drivers', component: DriverComponent, canActivate: [AuthGuard] },
  { path: 'maintenance', component: MaintenanceComponent, canActivate: [AuthGuard] },
  { path: 'insurance', component: InsuranceComponent, canActivate: [AuthGuard] },

  // Default route
  { path: '', redirectTo: '/dashboard', pathMatch: 'full' },

  // Optional: wildcard route (good practice)
  { path: '**', redirectTo: '/dashboard' }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {}