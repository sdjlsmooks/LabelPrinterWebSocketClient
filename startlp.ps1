function startlp() {
   Restart-Service -Name Spooler -Force
   Start-Service -DisplayName HS-AOPBACKDOOR
   Start-Service -DisplayName HS-AOPFrontDesk
   Start-Service -DisplayName HS-CARETEAM
   Start-Service -DisplayName HS-ClinicalSVCBackDoor
   Start-Service -DisplayName HS-Crestone1LabelPrinter
   Start-Service -DisplayName HS-Crestone2LabelPrinter
   Start-Service -DisplayName HS-CRISISGlassCube
   Start-Service -DisplayName HS-CRISISSTAFF
   Start-Service -DisplayName HS-CWCFrontDeskLabelPrinter
   Start-Service -DisplayName HS-DISCOVERYCENTER
   Start-Service -DisplayName HS-EDUCATIONALSERVICES
   Start-Service -DisplayName HS-LearningCenter
   Start-Service -DisplayName HS-MARCLabelPrinter
   Start-Service -DisplayName HS-MedicalCenterLabelPrinter
   Start-Service -DisplayName HS-RecoveryCenter
   Start-Service -DisplayName HS-Suite200LabelPrinter
   Start-Service -DisplayName HS-YFSBACKDOOR
   Start-Service -DisplayName HS-YFSDUTCHCLARKLabelPrinter
   Start-Service -DisplayName HS-YFSFrontDeskLabelPrinter
}
